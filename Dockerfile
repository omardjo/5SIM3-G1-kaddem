# Use a slim OpenJDK image as the base for running the application
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Expose the port the application runs on
EXPOSE 8089

# Copy the JAR file to the working directory
COPY target/kaddem-0.0.1-SNAPSHOT.jar kaddem.jar

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "kaddem.jar"]


