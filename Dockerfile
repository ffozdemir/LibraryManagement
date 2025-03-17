FROM openjdk:17-slim

WORKDIR /app

COPY target/librarymanagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]