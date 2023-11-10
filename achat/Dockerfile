# Use an official OpenJDK runtime as the base image
FROM adoptopenjdk:11-jre-hotspot

EXPOSE 8089
# Set the working directory in the container
WORKDIR /achat

# Copy the executable JAR file and any other necessary files
COPY target/achat-1.0.jar /achat/

# Set the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "achat-1.0.jar"]
