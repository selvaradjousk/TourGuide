FROM openjdk:8-jdk-alpine
ADD build/libs/tripDealsMicroservice-1.0.0.jar tripdeals-microservice.jar
EXPOSE 9093
ENTRYPOINT ["java","-jar","tripdeals-microservice.jar"]