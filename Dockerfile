FROM openjdk:17-oracle

WORKDIR /app

COPY target/paper-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7002

CMD ["java", "-jar", "app.jar"]