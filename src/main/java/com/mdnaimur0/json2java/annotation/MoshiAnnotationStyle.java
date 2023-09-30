package com.mdnaimur0.json2java.annotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoshiAnnotationStyle extends AnnotationStyle {
    
  @Override
  public List<String> getSignature(String key) {
    List<String> signs = new ArrayList<>();
    signs.add("@com.squareup.moshi.Json(name = \"" + key + "\")");
    return signs;
  }

  @Override
  public List<String> getImports() {
    return Collections.emptyList();
  }
}
