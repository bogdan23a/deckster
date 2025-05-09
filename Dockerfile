FROM openjdk:21-jdk
COPY target/*.jar backend.jar
WORKDIR /
ENTRYPOINT ["java","-jar","/backend.jar"]