package com.mdnaimur0.json2java;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdnaimur0.json2java.option.Options;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Json2JavaPojo {

  private final String LIST_IMPORT = "java.util.List";
  private final Map<String, String> nestedClasses = new HashMap<>();
  private final List<String> imports = new ArrayList<>();

  private Options options;
  private String className;
  private String packageName;
  private String source;

  private Json2JavaPojo(
          Options options,
          String className,
          String packageName,
          String source
  ) {
    this.options = options;
    this.className = className;
    this.packageName = packageName;
    this.source = source;
  }

  private void addToImports(String imp) {
    if (!imports.contains(imp)) {
      imports.add(imp);
    }
  }

  public String generate() throws Exception {
    ObjectMapper mapper = new ObjectMapper()
            .enable(JsonParser.Feature.ALLOW_COMMENTS);
    Map<String, Object> jsonData = mapper.treeToValue(mapper.readTree(source), Map.class);

    if (jsonData == null) throw new Exception("Json data is null");

    StringBuilder pojoStringBuilder = new StringBuilder();
    if (packageName != null) {
      pojoStringBuilder.append("package ").append(packageName).append(";\n\n");
    }
    if (options.getAnnotationStyle() != null) {
      for (String imp : options.getAnnotationStyle().getImports()) {
        pojoStringBuilder.append("import ").append(imp).append(";\n");
      }
    }
    String javaCode = createClassFromMap(jsonData, className);

    if (!imports.isEmpty()) {
      for (String imp : imports) {
        pojoStringBuilder.append("import ").append(imp).append(";\n");
      }
      pojoStringBuilder.append("\n");
    }
    if (options.getAnnotationStyle() != null) {
      for (String sign : options
              .getAnnotationStyle()
              .getParentClassSignature()) {
        pojoStringBuilder.append(sign).append("\n");
      }
    }
    pojoStringBuilder
            .append("public class ")
            .append(className)
            .append(" {\n")
            .append(javaCode);

    StringBuilder sb = new StringBuilder();
    for (String key : nestedClasses.keySet()) {
      String value = nestedClasses.get(key);
      sb.append("\n    public static class ").append(key).append(" {");
      for (String line : Objects.requireNonNull(value).split("\\r?\\n")) {
        sb.append("\n").append("    ").append(line);
      }
      sb.append("\n    }\n");
    }
    pojoStringBuilder.append(sb).append("}");
    return pojoStringBuilder.toString();
  }

  private String createClassFromMap(Map<String, Object> map, String className) {
    StringBuilder sb = new StringBuilder();
    Map<String, String> fieldsMap = new HashMap<>();

    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      String varName = getVariableNameFromKey(key);
      if (fieldsMap.containsKey(varName)) {
        int index = 2;
        while (fieldsMap.containsKey(varName + index)) index++;
        varName = varName + index;
      }
      String varType = "Object";
      if (value instanceof Map) {
        varType = getClassNameFromKey(key);
        nestedClasses.put(
                varType,
                createClassFromMap((Map<String, Object>) value, varType)
        );
      } else if (value instanceof Iterable) {
        List<Object> list = (List<Object>) value;
        varType =
                list.isEmpty()
                        ? "List<Object>"
                        : "List<" + getListType(list, key) + ">";
        addToImports(LIST_IMPORT);
      } else {
        varType = getValueType(value);
      }
      fieldsMap.put(varName, varType);
      if (options.getAnnotationStyle() != null) {
        for (String sign : options.getAnnotationStyle().getSignature(key)) {
          sb.append("    ").append(sign).append("\n");
        }
      }
      sb
              .append("    ")
              .append("private ")
              .append(varType)
              .append(" ")
              .append(varName)
              .append(";\n");
    }
    if (options.isIncludeConstructors()) {
      sb.append("\n");
      sb
              .append("    ")
              .append("public ")
              .append(className)
              .append("() {\n    }\n\n");
      if (!fieldsMap.isEmpty()) {
        sb
                .append("    ")
                .append("public ")
                .append(className)
                .append("(")
                .append(generateConstructorParams(fieldsMap))
                .append(") {\n")
                .append("        super();\n")
                .append(generateConstructorBody(fieldsMap, "        "))
                .append("    }\n");
      }
    }
    if (options.isIncludeGetter() || options.isIncludeSetter()) {
      sb.append("\n");
      for (String variableName : fieldsMap.keySet()) {
        String variableType = fieldsMap.get(variableName);
        if (options.isIncludeSetter()) {
          sb.append(getSetter(variableType, variableName));
          sb.append("\n\n");
        }
        if (options.isIncludeGetter()) {
          sb.append(getGetter(variableType, variableName));
          sb.append("\n\n");
        }
      }
    }
    return sb.toString();
  }

  private String generateConstructorBody(
          Map<String, String> fieldsMap,
          String spaces
  ) {
    StringBuilder sb = new StringBuilder();
    for (String key : fieldsMap.keySet()) {
      sb
              .append(spaces)
              .append("this.")
              .append(key)
              .append(" = ")
              .append(key)
              .append(";\n");
    }
    return sb.toString();
  }

  private String generateConstructorParams(Map<String, String> fieldsMap) {
    StringBuilder sb = new StringBuilder();
    Iterator<Map.Entry<String, String>> iterator = fieldsMap
            .entrySet()
            .iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> next = iterator.next();
      sb
              .append(next.getValue())
              .append(" ")
              .append(next.getKey())
              .append(iterator.hasNext() ? "," : "");
    }
    return sb.toString();
  }

  private String getListType(List<Object> list, String name) {
    Object firstElement = list.get(0);
    if (firstElement instanceof Map) {
      final Map<String, Object> modifiedMap = (Map<String, Object>) firstElement;
      for (Object object : list) {
        for (Map.Entry<String, Object> entry : (
                (Map<String, Object>) object
        ).entrySet()) {
          if (!modifiedMap.containsKey(entry.getKey())) {
            modifiedMap.put(entry.getKey(), entry.getValue());
          }
        }
      }
      String className = getClassNameFromKey(name);
      nestedClasses.put(className, createClassFromMap(modifiedMap, className));
      return className;
    } else if (firstElement instanceof Iterable) {
      addToImports(LIST_IMPORT);
      if (((List<Object>) firstElement).isEmpty()) {
        return "List<Object>";
      }
      String type = getListType((List<Object>) firstElement, name);
      return "List<" + type + ">";
    } else {
      return getValueType(firstElement);
    }
  }

  private String getSetter(String varType, String varName) {
    String methodSuffix =
            varName.substring(0, 1).toUpperCase() + varName.substring(1);
    return new StringBuilder()
            .append("    public void set")
            .append(methodSuffix)
            .append("(")
            .append(varType)
            .append(" ")
            .append(varName)
            .append(") {\n")
            .append("        this.")
            .append(varName)
            .append(" = ")
            .append(varName)
            .append(";\n    }")
            .toString();
  }

  private String getGetter(String varType, String varName) {
    String methodSuffix =
            varName.substring(0, 1).toUpperCase() + varName.substring(1);
    return new StringBuilder()
            .append("    public ")
            .append(varType)
            .append(" get")
            .append(methodSuffix)
            .append("() {\n        return this.")
            .append(varName)
            .append(";\n    }")
            .toString();
  }

  private String getValueType(Object value) {
    if (value instanceof String) return "String";
    if (
            value instanceof Integer || value instanceof Long
    ) return options.isUseLongIntegers()
            ? (options.isUsePrimitiveType() ? "long" : "Long")
            : (options.isUsePrimitiveType() ? "int" : "Integer");
    if (
            value instanceof Double || value instanceof Float
    ) return options.isUseDoubleNumbers()
            ? (options.isUsePrimitiveType() ? "double" : "Double")
            : (options.isUsePrimitiveType() ? "float" : "Float");
    return "Object";
  }

  private String getClassNameFromKey(String key) {
    String name = key.substring(0, 1).toUpperCase() + key.substring(1);
    if (!nestedClasses.containsKey(name)) return name;
    int index = 2;
    while (nestedClasses.containsKey(name + index)) {
      index++;
    }
    return name + index;
  }

  private String getVariableNameFromKey(String key) {
    return (
            key.replace("-", "_").replace(".", "").substring(0, 1).toLowerCase() +
                    key.substring(1)
    );
  }

  public static final class Builder {

    private Options options;
    private String className;
    private String packageName;
    private String source;

    public Builder setClassName(String className) {
      this.className = className;
      return this;
    }

    public Builder setPackageName(String packageName) {
      this.packageName = packageName;
      return this;
    }

    public Builder setOptions(Options options) {
      this.options = options;
      return this;
    }

    public Builder setSource(/* NotNull */String source) {
      this.source = source;
      return this;
    }

    public Json2JavaPojo build() {
      return new Json2JavaPojo(options, className, packageName, source);
    }
  }
}