FROM openjdk

WORKDIR /app/
COPY target/webCalculator-1.0.0.jar /app
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "webCalculator-1.0.0.jar"]
