FROM openjdk:13-oracle
EXPOSE 9090
ADD build/libs/tourguide-1.0.0.war tourguide.war
ENTRYPOINT ["java","-jar","tourguide.war"]