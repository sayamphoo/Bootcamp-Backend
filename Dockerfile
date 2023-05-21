## Base image for arm64/v8 (mac)
# FROM adoptopenjdk/openjdk11

# Base image for amd64 (EC2)
FROM amd64/amazoncorretto:11-alpine

WORKDIR /app

COPY target/BeePoint.jar BeePoint.jar
COPY ./src/main/resources/application.yml application.yml

EXPOSE 17003

ENTRYPOINT exec java -jar BeePoint.jar --spring.config.location=application.yml