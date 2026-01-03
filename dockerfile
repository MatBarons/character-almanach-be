# Stage 1: Build
FROM maven:3.9.12-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Stage 2: Run
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN useradd -r -u 1001 appuser
USER appuser
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]