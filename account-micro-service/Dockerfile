
#base image information
FROM openjdk:17-jdk-slim

#container maintainer information
MAINTAINER LABEL onkar2701.com

#copy file from local system to docker container file system
COPY /target/account-micro-service-0.0.1-SNAPSHOT.jar account-micro-service-0.0.1-SNAPSHOT.jar

#command that needs to be executed when we start/ run a docker container
ENTRYPOINT ["java","-jar","account-micro-service-0.0.1-SNAPSHOT.jar"]