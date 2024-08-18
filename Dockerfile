# 베이스 이미지 설정
FROM openjdk:17-jdk-alpine

# 애플리케이션 JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} adopp.jar

# 실행할 명령어 정의
ENTRYPOINT ["java", "-jar", "/LightHouseAI-0.0.1-SNAPSHOT.jar"]