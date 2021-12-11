FROM openjdk:13-oracle
ADD build/libs/rewardsMicroservice-1.0.0.war rewards-microservice.war
EXPOSE 9092
ENTRYPOINT ["java","-jar","rewards-microservice.war"]