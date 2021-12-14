<img src="https://img.shields.io/badge/java-%23ED8B00.svg?&style=for-the-badge&logo=java&logoColor=white"/> *** <img src="https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white"/> *** <img src="https://img.shields.io/badge/docker%20-%230db7ed.svg?&style=for-the-badge&logo=docker&logoColor=white"/> *** <img src="https://img.shields.io/badge/gradle-%23ED8B00.svg?&style=for-the-badge&logo=gradle&logoColor=white"/>

<h1 align="center">TourGuide</h1>

TourGuide is a game changer Spring Boot web application with MSA (MicroService Architecure) technologies developed by TripMaster.
The strong highlight features of the application's Architecture is that it resonates through its rich functionality for its flexible scalability & high availability .
<br>

<a href="#"><img width="98%" src="tourguide/asserts/reporting/touguide_image.png" alt="TOURGUIDE IMAGE"></a><br>

 It is available as a web interface both on PC & mobile platforms for all touristic users.

 ### Key features
- Helps explore and discover attractions available near user's travel location;
- Provides reliable and up-to-date real-time information on the discounts for Travel, Hotel reservation, Touristic Attractions Ticket offers, etc.;
- Personalised serach information based on the user's favorite preferences related to touristic attractions and travel offers.

To meet the explosive growth on the touristic user's client base, architecture redesigned is being implemented in this project to optimize performance for high volume user demands.


## Technological Spec & Run Prerequisites

- Java 1.8 JDK
- Gradle 7.3
- Docker

## Architectural Spec:

TourGuide application is composed of 4 microservices:

-  **TourGuide**
-  **gps-ms (microservice)**
- **rewards-ms (microservice)**
- **tripDeals-ms (microservice)**

<a href="#"><img width="98%" src="tourguide/asserts/reporting/mvc_tourguide1.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_tourguide.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_microservice_gps.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mvc_tourguide2.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_microservice_rewards.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_microservice_tripdeals.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>


## Application Run configuration

## Gradle 
```
gradle bootRun or ./gradle bootRun
```
```
gradle bootWar or ./gradle bootWar or ./gradle bootJar
```

## Docker

### Building Docker images

Use the **Dockerfile** on the package roots containing individual 4 services to build docker images

SYNTAX:
```
docker build . -f Dockerfile -t imageNameToBeCreated
```

### Running a Docker image

Use the  **DockerImage** created above & run a Docker image using the command below

SYNTAX:
```
docker run -d -p HostPort:InternalAppPort --name dockerContainerNameToBeCreated -d DockerImageName
```

### Docker Compose

In case, if want to use an automated multi-container workflow with docker-compose, follow details below:

To deploy all TourGuide microservices in a single go, use the **docker-compose.yml** on the package root containing all 4 services that will orchestrate multiple containers that work together based on the defined configuration in it.

SYNTAX:
```
docker-compose up -d
```

## Testing

Gradle, Junit (Unit & Integration Tests). <br/>

SYNTAX:
```
gradlew test or ./gradlew test or gradlew clean test
```



## Reporting

 ### Test Results <br/>
<img src="https://img.shields.io/badge/gradle-%23ED8B00.svg?&style=for-the-badge&logo=gradle&logoColor=white"/><br>
 <a href="#"><img width="98%" src="tourguide/asserts/reporting/gradle_build_report_20211205_01.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>

<img src="https://img.shields.io/badge/JaCoCo-brightgreen?&style=for-the-badge&logo=jacoco&logoColor=white"/><br>
 <a href="#"><img width="98%" src="tourguide/asserts/reporting/jacoco_20211205_01.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br>
 
<img src="https://img.shields.io/badge/JUnit-blue?&style=for-the-badge&logo=junit&logoColor=white"/><br>
 <a href="#"><img width="98%" src="tourguide/asserts/reporting/Junit_test_20211205.PNG" alt="TOURGUIDE IMAGE"></a><br><br><br><br>
 



# Metrics
Test Performance on highVolume User Tracking & User Rewards Computations are performed & available.

## HighVolume User Tracking Report - Graph
<a href="#"><img width="98%" src="tourguide/asserts/reporting/Performance_Location_graph_20211207.PNG" alt="Performance report on User Location"></a><br>

## HighVolume Rewards Calculation Report - Graph
<a href="#"><img width="98%" src="tourguide/asserts/reporting/Performance_rewards_graph_20211207.PNG" alt="Performance report on user rewards"></a><br><br><br>


## API (Endpoints) documentation 

All endpoints are documented with POSTMAN and can be accessed launched with the below link to POSTMAN:

[POSTMAN - TOURGUIDE APIs](https://documenter.getpostman.com/view/16200863/UVR5sV8W)<br><br><br>




### Authors
Mentee:  🡆   @Senthil<br>
Mentor:  🡆   Clément SEZETTRE<br><br>

### versions
Version:  🡆 1.0<br><br>

### License
@OpenClassrooms & @TourGuide<br><br>



Reference Documentation
===
For further reference, consider the following sections:


* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Docker docs](https://docs.docker.com/)
* [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
* [STAN DOCUMENTATION WHITE PAPER](http://stan4j.com/papers/stan-whitepaper.pdf) 


Reference Guides
===
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [STAN Structure Analysis for Java](http://stan4j.com/)