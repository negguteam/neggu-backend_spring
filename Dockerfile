FROM openjdk:21-jdk-slim
COPY build/libs/neggu.jar neggu.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "neggu.jar"]
# ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "/neggu.jar"]