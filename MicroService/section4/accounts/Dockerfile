#Start with a base image contining Java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER muscatHan

#Add the application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#excute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]
