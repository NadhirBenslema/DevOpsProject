# Use an official OpenJDK runtime as the base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /achat

# Copy the executable JAR file and any other necessary files
COPY target/achat-1.0.jar .

EXPOSE 8089

# Set the command to run the Spring Boot application
CMD ["java", "-jar", "achat-1.0.jar"]
