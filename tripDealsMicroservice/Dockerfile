FROM openjdk:13-oracle
ADD build/libs/tripDealsMicroservice-1.0.0.war tripdeals-microservice.war
EXPOSE 9093
ENTRYPOINT ["java","-jar","tripdeals-microservice.war"]