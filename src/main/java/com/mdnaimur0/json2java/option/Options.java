package com.mdnaimur0.json2java.option;

import com.mdnaimur0.json2java.annotation.AnnotationStyle;

public abstract class Options {

  public abstract AnnotationStyle getAnnotationStyle();

  public abstract boolean isIncludeGetter();

  public abstract boolean isIncludeSetter();

  public abstract boolean isUsePrimitiveType();

  public abstract boolean isUseLongIntegers();

  public abstract boolean isUseDoubleNumbers();

  public abstract boolean isIncludeConstructors();

  public static final class Builder {

    private AnnotationStyle annotationStyle;
    private boolean isIncludeGetter;
    private boolean isIncludeSetter;
    private boolean isUsePrimitiveType;
    private boolean isUseLongIntegers;
    private boolean isUseDoubleNumbers;
    private boolean isIncludeConstructors;

    public Builder setAnnotationStyle(AnnotationStyle annotationStyle) {
      this.annotationStyle = annotationStyle;
      return this;
    }

    public Builder isIncludeGetter(boolean isIncludeGetter) {
      this.isIncludeGetter = isIncludeGetter;
      return this;
    }

    public Builder isIncludeSetter(boolean isIncludeSetter) {
      this.isIncludeSetter = isIncludeSetter;
      return this;
    }

    public Builder isUsePrimitiveType(boolean isUsePrimitiveType) {
      this.isUsePrimitiveType = isUsePrimitiveType;
      return this;
    }

    public Builder isUseLongIntegers(boolean isUseLongIntegers) {
      this.isUseLongIntegers = isUseLongIntegers;
      return this;
    }

    public Builder isUseDoubleNumbers(boolean isUseDoubleNumbers) {
      this.isUseDoubleNumbers = isUseDoubleNumbers;
      return this;
    }

    public Builder isIncludeConstructors(boolean isIncludeConstructors) {
      this.isIncludeConstructors = isIncludeConstructors;
      return this;
    }

    public Options build() {
      return new Options() {
        @Override
        public AnnotationStyle getAnnotationStyle() {
          return annotationStyle;
        }

        @Override
        public boolean isIncludeGetter() {
          return isIncludeGetter;
        }

        @Override
        public boolean isIncludeSetter() {
          return isIncludeSetter;
        }

        @Override
        public boolean isUsePrimitiveType() {
          return isUsePrimitiveType;
        }

        @Override
        public boolean isUseLongIntegers() {
          return isUseLongIntegers;
        }

        @Override
        public boolean isUseDoubleNumbers() {
          return isUseDoubleNumbers;
        }

        @Override
        public boolean isIncludeConstructors() {
          return isIncludeConstructors;
        }
      };
    }
  }
}
