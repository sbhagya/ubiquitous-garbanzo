FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/recipe-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} recipe-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/recipe-api.jar"]