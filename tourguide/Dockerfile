FROM openjdk:8-jdk-alpine
EXPOSE 9090
ADD build/libs/tourguide-1.0.0.jar tourguide.jar
ENTRYPOINT ["java","-jar","tourguide.jar"]