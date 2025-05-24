FROM openjdk:23
LABEL authors="HP"
WORKDIR /app
COPY target/Facultad-Microservice-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "Facultad-Microservice-0.0.1-SNAPSHOT.jar"]