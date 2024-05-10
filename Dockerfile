FROM openjdk:21-slim-bullseye

COPY target/SE_Projekt_Car_Rental_Service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]