FROM openjdk:21-jdk
COPY /build/libs/deckster-0.0.1-SNAPSHOT.jar backend.jar
WORKDIR /
ENTRYPOINT ["java","-jar","/backend.jar"]