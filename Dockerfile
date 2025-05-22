FROM openjdk:21-jdk
COPY build/libs/*.jar backend.jar
WORKDIR /
ENTRYPOINT ["java","-jar","/backend.jar"]