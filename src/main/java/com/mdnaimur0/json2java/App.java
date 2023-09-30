package com.mdnaimur0.json2java;

import com.mdnaimur0.json2java.annotation.GsonAnnotationStyle;
import com.mdnaimur0.json2java.option.Options;

import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

  public static void main(String[] args) throws Exception {
    Options options = new Options.Builder()
      .isIncludeConstructors(true)
      .isUsePrimitiveType(true)
      .isIncludeSetter(true)
      .setAnnotationStyle(new GsonAnnotationStyle())
      .build();
    Json2JavaPojo javaPojo = new Json2JavaPojo.Builder()
      .setClassName("MyClass")
      .setPackageName("com.mdnaimur0.json2java")
      .setSource(readJsonFile("sample.json"))
      .setOptions(options)
      .build();
    System.out.println(javaPojo.generate());
  }

  public static String readJsonFile(String fileName) throws Exception {
    String data = "";
    try {
      data = new String(Files.readAllBytes(Paths.get(fileName)));
    } catch (Exception e) {
      throw new Exception("File not found");
    }
    return data;
  }
}