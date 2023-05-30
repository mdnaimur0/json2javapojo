package com.mdnaimu0.json2java;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class Json2JavaPojo {

  private Map<String, String> nestedClasses = new HashMap<>();

  public Json2JavaPojo() {}

  public void start() {
    File jsonFile = new File("sample.json");

    try {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> jsonData = mapper.readValue(jsonFile, Map.class);

      StringBuilder pojoStringBuilder = new StringBuilder();
      String javaCode = generateFromMap(jsonData);
      pojoStringBuilder.append("public class MyClass {\n").append(javaCode);

      StringBuilder sb = new StringBuilder();
      for (String key : nestedClasses.keySet()) {
        String value = nestedClasses.get(key);
        sb.append("\n    public static class ").append(key).append(" {");
        for (String line : value.lines().toList()) {
          sb.append("\n").append("    ").append(line);
        }
        sb.append("\n    }\n");
      }
      pojoStringBuilder.append(sb).append("\n}").toString();

      System.out.println(pojoStringBuilder.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String generateFromMap(Map<String, Object> map) {
    StringBuilder sb = new StringBuilder();
    Map<String, String> fieldsMap = new HashMap<>();

    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      String variableName = getVariableNameFromKey(key);
      if (fieldsMap.containsKey(variableName)) {
        int index = 2;
        while (fieldsMap.containsKey(variableName + index)) index++;
        variableName = variableName + index;
      }

      if (value instanceof Map) {
        String name = getClassNameFromKey(key);
        sb
          .append("    private ")
          .append(name)
          .append(" ")
          .append(variableName)
          .append(";\n");
        fieldsMap.put(variableName, name);
        nestedClasses.put(name, generateFromMap((Map<String, Object>) value));
      } else if (value instanceof Iterable) {
        List<Object> list = (List<Object>) value;
        if (list.isEmpty()) {
          sb
            .append("    private List<Object> ")
            .append(variableName)
            .append(";\n");
          fieldsMap.put(variableName, "List<Object>");
        } else {
          String type = getListType(list, key);
          sb
            .append("    private List<")
            .append(type)
            .append("> ")
            .append(variableName)
            .append(";\n");
          fieldsMap.put(variableName, "List<" + type + ">");
        }
      } else {
        String type = getValueType(value);
        sb
          .append("    private ")
          .append(type)
          .append(" ")
          .append(variableName)
          .append(";\n");
        fieldsMap.put(variableName, type);
      }
    }
    sb.append("\n");
    for (String variableName : fieldsMap.keySet()) {
      String variableType = fieldsMap.get(variableName);
      sb.append(getSetter(variableType, variableName));
      sb.append("\n\n");
      sb.append(getGetter(variableType, variableName));
      sb.append("\n\n");
    }
    return sb.toString();
  }

  private String getListType(List<Object> list, String name) {
    Object firstElement = list.get(0);
    if (firstElement instanceof Map) {
      final Map<String, Object> modifiedMap = (Map<String, Object>) firstElement;
      for (Object object : list) {
        ((Map<String, Object>) object).forEach(
            new BiConsumer<String, Object>() {
              @Override
              public void accept(String key, Object value) {
                if (!modifiedMap.containsKey(key)) {
                  modifiedMap.put(key, value);
                }
              }
            }
          );
      }
      String className = getClassNameFromKey(name);
      nestedClasses.put(className, generateFromMap(modifiedMap));
      return className;
    } else if (firstElement instanceof Iterable) {
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
    if (value instanceof Integer) return "int";
    if (value instanceof Double) return "double";
    if (value instanceof Float) return "float";
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
}
