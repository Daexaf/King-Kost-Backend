FROM openjdk:17
WORKDIR /app
COPY target/king-kost-0.0.1-SNAPSHOT.jar /app/king-kost-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/app/king-kost-0.0.1-SNAPSHOT.jar"]