# 첫 번째 단계: 빌드 스테이지
FROM gradle:jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src
RUN gradle clean build --no-daemon

ARG MONGO_PORT
ARG JASYPT_PASSWORD
ARG EUREKA_URL

ENV MONGO_PORT=${MONGO_PORT}
ENV JASYPT_PASSWORD=${JASYPT_PASSWORD}
ARG EUREKA_URL=${EUREKA_URL}

# 두 번째 단계: 실행 스테이지
FROM openjdk:17-slim
EXPOSE 7070
WORKDIR /app
ARG MONGO_PORT
ARG JASYPT_PASSWORD
ARG EUREKA_URL

ENV MONGO_PORT=${MONGO_PORT}
ENV JASYPT_PASSWORD=${JASYPT_PASSWORD}
ARG EUREKA_URL=${EUREKA_URL}
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
