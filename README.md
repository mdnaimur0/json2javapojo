
## JSON to Java Pojo Generator

A light-weight and simple project to generate Java pojo classes from JSON.

Here is how to use:
- First build the options as you need
```java
Options options = new Options.Builder()
	.isIncludeConstructors(true)
	.isUsePrimitiveType(true)
	.isIncludeSetter(true)
	.setAnnotationStyle(new GsonAnnotationStyle())
	.build();
```

- Then generate your desired pojo class

```java
Json2JavaPojo javaPojo = new Json2JavaPojo.Builder()
	.setClassName("MyClass")
	.setPackageName("com.example.models")
	.setSource("put-your-json-here")
	.setOptions(options)
	.build();
	
String pojoClassCode = javaPojo.generate();
System.out.println(pojoClassCode);
```