# Use the official OpenJDK 8 image as the base image
FROM openjdk:8
EXPOSE 8089
ADD target/achat-1.0.jar achat-1.0.jar

# Define the entry point for your application
ENTRYPOINT ["java", "-jar", "achat.jar"]