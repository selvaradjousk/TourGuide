FROM openjdk:8-jdk-alpine
ADD build/libs/rewardsMicroservice-1.0.0.jar rewards-microservice.jar
EXPOSE 9092
ENTRYPOINT ["java","-jar","rewards-microservice.jar"]