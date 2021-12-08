FROM openjdk:8-jdk-alpine
ADD build/libs/gpsMicroservice-1.0.0.jar gps-microservice.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar","gps-microservice.jar"]