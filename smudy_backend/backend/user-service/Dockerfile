# 첫 번째 단계: 빌드 스테이지
FROM gradle:jdk17 AS build
WORKDIR /home/gradle/src
ARG MONGO_PORT
ARG JASYPT_PASSWORD
ARG EUREKA_URL

ENV MONGO_PORT=${MONGO_PORT}
ENV JASYPT_PASSWORD=${JASYPT_PASSWORD}
ENV EUREKA_URL=${EUREKA_URL}
COPY --chown=gradle:gradle . /home/gradle/src
RUN gradle clean build --no-daemon



# 두 번째 단계: 실행 스테이지
FROM openjdk:17-slim
WORKDIR /app
ARG MONGO_PORT
ARG JASYPT_PASSWORD
ARG EUREKA_URL

ENV MONGO_PORT=${MONGO_PORT}
ENV JASYPT_PASSWORD=${JASYPT_PASSWORD}
ENV EUREKA_URL=${EUREKA_URL}
ENV TZ=Asia/Seoul

# 타임존 설정을 위해 tzdata 패키지 설치
RUN apt-get update && apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && \
    apt-get clean && rm -rf /var/lib/apt/lists/* \

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
