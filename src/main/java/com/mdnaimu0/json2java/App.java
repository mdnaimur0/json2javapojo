package com.mdnaimu0.json2java;

public class App {

  public static void main(String[] args) {
    Json2JavaPojo javaPojo = new Json2JavaPojo();
    javaPojo.start();
  }
}

/*
 * Annotation style:
 * Jackson 2.x , Gson, Moshi, JSON-B 1.x, JSON-B 2.x, None
 * 
 * Generate builder methods
 * Use primitive types
 * Use long integers
 * Use double numbers
 * Include getters and setters
 * Include constructors
 * Include hashCode and equals
 * Include toString
 * Allow additional properties
 * Make classes serializable
 * Make classes parcelable
 * Initialize collections
 * Class & variable naming ()
 */
