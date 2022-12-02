# Step: Build
FROM openjdk:17.0.2-jdk-slim-buster AS builder

WORKDIR /app
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src/main ./src/main
RUN ./gradlew bootJar

# Step: Run
FROM openjdk:17.0.2-slim-buster

WORKDIR /app
COPY --from=builder /app/build/libs/sample-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]