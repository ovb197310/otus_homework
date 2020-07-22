FROM bellsoft/liberica-openjdk-alpine:11
COPY target/hw-2.0.1-SNAPSHOT.jar /app/hw-2.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/hw-2.0.1-SNAPSHOT.jar"]
