# # กำหนดเป็นภาพหลักของ Java
# FROM openjdk:11

# # สร้างไดเร็กทอรีที่จะใช้เป็นพื้นที่ทำงานในคอนเทนเนอร์
# WORKDIR /app

# # คัดลอกไฟล์ JAR จากโฟลเดอร์ target ของโปรเจคของคุณไปยังคอนเทนเนอร์
# COPY target/*.jar BeePoint.jar

# # กำหนดพอร์ตที่แอปพลิเคชันจะใช้ภายในคอนเทนเนอร์
# EXPOSE 17003

# # กำหนดคำสั่งที่จะถู กเรียกเมื่อเริ่มต้นคอนเทนเนอร์
# CMD ["java", "-jar", "BeePoint.jar"]

# ============================================================================================
# ข้างบนใช้ได้แหละ แต่อยากให้มันใส่ environment ได้

## Base image for arm64/v8 (mac)
#FROM amazoncorretto:11-alpine

# Base image for amd64 (EC2)
FROM amd64/amazoncorretto:11-alpine

WORKDIR /app

COPY target/BeePoint.jar BeePoint.jar
COPY ./src/main/resources/application.yml application.yml

EXPOSE 17003

ENTRYPOINT exec java --enable-preview -jar BeePoint.jar --spring.config.location=application.yml