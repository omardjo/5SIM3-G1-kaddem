# Stage 1: Build the JAR file
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy only the pom.xml and the main source code
COPY pom.xml . 
COPY src/main ./src/main

# Install dependencies and package the application
RUN mvn package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/kaddem-0.0.1-SNAPSHOT.jar app.jar

# Install netcat (nc) to check MySQL availability
RUN apt-get update && apt-get install -y netcat

# Script to wait for MySQL to be ready
COPY wait-for-mysql.sh /wait-for-mysql.sh
RUN chmod +x /wait-for-mysql.sh

# Command to wait for MySQL to be ready and then start the Spring Boot application
CMD ["/wait-for-mysql.sh", "mysql", "3306", "30", "java", "-jar", "app.jar"]

