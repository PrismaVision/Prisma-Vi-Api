FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/prisma-vi-1.jar

COPY --from=build /app/${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
