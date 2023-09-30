package com.mdnaimur0.json2java.option;

import com.mdnaimur0.json2java.annotation.AnnotationStyle;

public class DefaultOptions extends Options {

  public AnnotationStyle getAnnotationStyle() {
    return null;
  }

  public boolean isUsePrimitiveType() {
    return false;
  }

  public boolean isUseLongIntegers() {
    return false;
  }

  public boolean isUseDoubleNumbers() {
    return true;
  }

  public boolean isIncludeConstructors() {
    return false;
  }

  @Override
  public boolean isIncludeGetter() {
    return true;
  }

  @Override
  public boolean isIncludeSetter() {
    return true;
  }
}
