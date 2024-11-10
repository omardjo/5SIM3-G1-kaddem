# Use an OpenJDK image as the base for running the application
FROM openjdk:11-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Expose the port the application runs on (update if different)
EXPOSE 8089

# Add the JAR file to the working directory
ADD target/kaddem-0.0.1-SNAPSHOT.jar kaddem.jar

# Define the entry point to run the application, using the correct JAR name
ENTRYPOINT ["java", "-jar", "kaddem.jar"]

