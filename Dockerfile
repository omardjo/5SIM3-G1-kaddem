# Étape de build avec Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copier tous les fichiers nécessaires pour le build
COPY . .



# Étape finale avec l'image OpenJDK légère
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copier l'artifact jar depuis l'étape de build
COPY --from=build /app/target/kaddem-0.0.1-SNAPSHOT.jar app.jar

# Lancer l'application avec un délai de 30 secondes
CMD ["sh", "-c", "sleep 30 && java -jar app.jar"]

