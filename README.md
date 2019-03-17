# Recipe API

## Description
This API provide recipes based on available ingredients. Ingredients which are not expired are considered to determine recipes. If there are ingredients with past best-before dates, they will be sorted to the bottom of the recipes list. 

## Requirements
For building and running the application you need:
- Java 1.8 or later.
- Maven 3.5 or later.

## Installation
Simply clone or download GIT repository

## Running the application
- You can use spring-boot-maven-plugin to run the application. Navigate to root of the application folder, then run

```
mvn spring-boot:run
 ```

- You can build the jar file and run the application

```
mvn clean install
// Navigate to target
java -jar recipe-api-0.0.1-SNAPSHOT.jar
```


 

