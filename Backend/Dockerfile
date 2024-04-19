FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /workspace/app
COPY . .

# Make the Build
RUN ./gradlew build

#RUN gradle build

FROM eclipse-temurin:21-jdk-jammy

COPY --from=build /workspace/app/build/libs/DigitalForms-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]