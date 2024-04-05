FROM openjdk:17-oracle

WORKDIR /app
RUN mkdir -p /app/profile
COPY backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
