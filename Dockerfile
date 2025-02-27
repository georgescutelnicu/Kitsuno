FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
RUN apt-get update && apt-get install -y python3 python3-pip  
RUN pip3 install --no-cache-dir genanki  
COPY --from=build /target/kitsuno-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]