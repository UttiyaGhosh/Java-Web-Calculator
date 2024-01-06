FROM openjdk

WORKDIR /app/
ADD target/calculator-1.0.0.jar /app
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "calculator-1.0.0.jar.jar"]
