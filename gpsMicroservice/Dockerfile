FROM openjdk:13-oracle
ADD build/libs/gpsMicroservice-1.0.0.war gps-microservice.war
EXPOSE 9091
ENTRYPOINT ["java","-jar","gps-microservice.war"]