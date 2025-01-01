FROM openjdk:21-jdk-slim
ARG PROFILE=beta
ENV PROFILE=${PROFILE}
ARG ENCRYPT_KEY=neggu-develop
ENV ENCRYPT_KEY=${ENCRYPT_KEY}
COPY build/libs/neggu.jar neggu.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-DencryptKey=${ENCRYPT_KEY}", "/neggu.jar"]