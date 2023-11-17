FROM openjdk:8
EXPOSE 8089
ENV NEXUS_USERNAME=admin
ENV NEXUS_PASSWORD=alamadara1998
ENV NEXUS_REPO_URL=http://192.168.1.73:8081/repository/maven-releases/tn/esprit/rh/achat/1.0/achat-1.0.jar

# Download the JAR file from Nexus and copy it to the container
RUN curl -L -o achat.jar -u $NEXUS_USERNAME:$NEXUS_PASSWORD $NEXUS_REPO_URL

# Define the entry point for your application
ENTRYPOINT ["java", "-jar", "achat.jar"]