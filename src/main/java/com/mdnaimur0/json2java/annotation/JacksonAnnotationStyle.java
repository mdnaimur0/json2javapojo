package com.mdnaimur0.json2java.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JacksonAnnotationStyle extends AnnotationStyle {

  @Override
  public List<String> getSignature(String key) {
    List<String> signs = new ArrayList<>();
    signs.add("@JsonProperty(\"" + key + "\")");
    return signs;
  }

  @Override
  public List<String> getImports() {
    return Arrays.asList(
      new String[] {
        "com.fasterxml.jackson.annotation.JsonInclude",
        "com.fasterxml.jackson.annotation.JsonProperty",
      }
    );
  }

  @Override
  public List<String> getParentClassSignature() {
    return Arrays.asList(
      new String[] { "@JsonInclude(JsonInclude.Include.NON_NULL)" }
    );
  }
}
