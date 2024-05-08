# 첫 번째 단계: 빌드 스테이지
FROM gradle:jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src
RUN gradle clean build --no-daemon

ARG EUREKA_URL
ENV EUREKA_URL=${EUREKA_URL}

# 두 번째 단계: 실행 스테이지
FROM openjdk:17-slim
<<<<<<<< HEAD:smudy_backend/backend/search-service/Dockerfile
========
EXPOSE 8762
>>>>>>>> deploy_be_discovery:smudy_backend/backend/discovery-service/Dockerfile
WORKDIR /app
ARG EUREKA_URL
ENV EUREKA_URL=${EUREKA_URL}
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ARG EUREKA_URL
ENV EUREKA_URL=${EUREKA_URL}
CMD ["java", "-jar", "app.jar"]
