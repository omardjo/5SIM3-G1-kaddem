
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app


COPY pom.xml .
COPY src/main ./src/main


RUN mvn package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app


COPY --from=build /app/target/kaddem-0.0.1-SNAPSHOT.jar app.jar


CMD ["sh", "-c", "sleep 30 && java -jar app.jar"]
