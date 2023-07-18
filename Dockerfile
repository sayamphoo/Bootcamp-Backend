
FROM amd64/amazoncorretto:11-alpine

WORKDIR /app

COPY target/BeePoint.jar BeePoint.jar
COPY ./src/main/resources/application.yml application.yml

EXPOSE 17003

ENTRYPOINT exec java --enable-preview -jar BeePoint.jar --spring.config.location=application.yml