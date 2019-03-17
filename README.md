# Recipe API

## Description
This API provide recipes based on available ingredients. Ingredients which are not expired are considered to determine recipes. If there are ingredients with past best-before dates, they will be sorted to the bottom of the recipes list. 

## Usage
Example usage: Use http://localhost:8080/lunch API call to retrieve available lunch recipes.  

Update ingredients.json and recipe.json files in src/main/resources folder as required to get result relevant to the current date. 

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

- You can create a docker image and run the application as a docker container(You must have docker installed in your system)

```
docker build -t recipe-api .
```

To run the created docker,

```
docker run -d -p 5000:8080 recipe-api
```

## Test data
There are test data files under /src/test/resources. Update these file as needed to validate unit test cases.

Integration test cases need to be updated according to the ingredients.json and recipe.json files located at src/main/resources
