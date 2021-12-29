FROM adoptopenjdk/openjdk11:latest
ADD target/transit-ticketing-client-backend.jar transit-ticketing-client-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "transit-ticketing-client-backend.jar"]