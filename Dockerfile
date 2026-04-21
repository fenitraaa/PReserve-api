FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="tojonirinafenitra8@gmail.com"

WORKDIR /app

COPY target/preserve-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
