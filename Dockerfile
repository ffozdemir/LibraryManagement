
FROM openjdk:17-slim AS build
WORKDIR /app

COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
COPY src ./src

RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "-Dserver.port=${PORT:-8080}", "app.jar"]

EXPOSE 8080