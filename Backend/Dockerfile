FROM maven:3.9.7-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
COPY mvnw.cmd ./

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Package the application
RUN mvn package


FROM openjdk:23-ea-21-slim-bookworm

WORKDIR /app

# Install chromium to generate PDF Files
RUN apt update
RUN apt-get install -y chromium

COPY --from=builder /app/target/DigitalForms-0.0.1-SNAPSHOT.jar  /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

