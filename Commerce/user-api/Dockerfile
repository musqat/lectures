FROM openjdk:11-jre-slim
COPY build/libs/user-api.jar user-api.jar
ENTRYPOINT ["java", "-DSpring.profiles.active=prod", "-Dmailgun-Key=${MAILGUN_KEY}", "-jar", "user-api.jar"]