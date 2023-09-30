package com.mdnaimur0.json2java.annotation;

import java.util.Collections;
import java.util.List;

public abstract class AnnotationStyle {

  public abstract List<String> getSignature(String key);

  public abstract List<String> getImports();

  public List<String> getParentClassSignature() {
    return Collections.emptyList();
  }
}
