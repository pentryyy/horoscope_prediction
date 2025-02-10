FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY target/horoscope_prediction-0.0.1-SNAPSHOT.jar horoscope_prediction.jar
ENTRYPOINT ["java", "-jar", "horoscope_prediction.jar"]
EXPOSE 9090