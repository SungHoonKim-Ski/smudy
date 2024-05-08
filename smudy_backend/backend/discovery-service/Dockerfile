# 첫 번째 단계: 빌드 스테이지
FROM gradle:jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src

ARG EUREKA_URL
ENV EUREKA_URL=${EUREKA_URL}
RUN gradle clean build --no-daemon


# 두 번째 단계: 실행 스테이지
FROM openjdk:17-slim
WORKDIR /app
ARG EUREKA_URL
ENV EUREKA_URL=${EUREKA_URL}
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
