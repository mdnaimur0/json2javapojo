package com.mdnaimur0.json2java.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonAnnotationStyle extends AnnotationStyle {

  @Override
  public List<String> getSignature(String key) {
    List<String> signs = new ArrayList<>();
    signs.add("@SerializedName(\"" + key + "\")");
    signs.add("@Expose");
    return signs;
  }

  @Override
  public List<String> getImports() {
    return Arrays.asList(
      new String[] {
        "com.google.gson.annotations.Expose",
        "com.google.gson.annotations.SerializedName",
      }
    );
  }
}
