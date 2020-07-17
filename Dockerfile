FROM bellsoft/liberica-openjdk-alpine:11
COPY target/hw-1.0.0-SNAPSHOT.jar /app/hw-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/hw-1.0.0-SNAPSHOT.jar"]
