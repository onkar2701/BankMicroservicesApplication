FROM openjdk:17-jdk-slim

#Information around who maintains this image
MAINTAINER onkar2701.com

#copy data from local file system to docker container
#add app jar to image
COPY target/account-micro-service-0.0.1-SNAPSHOT.jar account-micro-service-0.0.1-SNAPSHOT.jar

# execute the application
# run this command in this docker container
ENTRYPOINT ["java","-jar","account-micro-service-0.0.1-SNAPSHOT.jar"]

