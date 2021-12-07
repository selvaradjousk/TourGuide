<img src="https://img.shields.io/badge/java-%23ED8B00.svg?&style=for-the-badge&logo=java&logoColor=white"/> *** <img src="https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white"/> *** <img src="https://img.shields.io/badge/docker%20-%230db7ed.svg?&style=for-the-badge&logo=docker&logoColor=white"/> *** <img src="https://img.shields.io/badge/gradle-%23ED8B00.svg?&style=for-the-badge&logo=gradle&logoColor=white"/>

<h1 align="center">TourGuide</h1>

TourGuide is a game changer Spring Boot web application with MSA (MicroService Architecure) technologies developed by TripMaster.
The strong highlight features of the application's Architecture is that it resonates through its rich functionality for its flexible scalability & high availability .
<br>

<a href="#"><img width="98%" src="tourguide/asserts/reporting/touguide_image.png" alt="TOURGUIDE IMAGE"></a><br>

 It is available as a web interface both on PC & mobile platforms for all touristic users.

 Key features
- Helps explore and discover attractions available near user's travel location;
- Provides reliable and up-to-date real-time information on the discounts for Travel, Hotel reservation, Touristic Attractions Ticket offers, etc.;
- Personalised serach information based on the user's favorite preferences related to touristic attractions and travel offers.

To meet the explosive growth on the touristic user's client base, architecture redesigned is being implemented in this project to optimize performance for high volume user demands.


## Prerequisites to run

- Java 1.8 JDK
- Gradle 7.3
- Docker

## Technological Spec Stacks:

Java, Gradle & Docker

## Architectural Spec:

TourGuide application is composed of 4 microservices:

-  **TourGuide**
-  **gps-ms (microservice)**
- **rewards-ms (microservice)**
- **tripDeals-ms (microservice)**

<a href="#"><img width="98%" src="tourguide/asserts/reporting/mvc_tourguide1.PNG" alt="TOURGUIDE IMAGE"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_tourguide.PNG" alt="TOURGUIDE IMAGE"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_microservice_gps.PNG" alt="TOURGUIDE IMAGE"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mvc_tourguide2.PNG" alt="TOURGUIDE IMAGE"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_microservice_rewards.PNG" alt="TOURGUIDE IMAGE"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/mcd_microservice_tripdeals.PNG" alt="TOURGUIDE IMAGE"></a><br>

 // TODO - documentation under progress

## Application Run configuration

Gradle 
```
gradle bootRun
```
## Docker deploiement

- **Dockerfile** 
 // TODO - documentation under progress

## Endpoints

 // TODO - documentation under progress


## Testing

Gradle, Junit (Unit & Integration Tests). <br/>
 

## Reporting

 - FinishLine. <br/>

 <a href="#"><img width="98%" src="tourguide/asserts/reporting/gradle_build_report_20211205_01.PNG" alt="TOURGUIDE IMAGE"></a><br>
 <a href="#"><img width="98%" src="tourguide/asserts/reporting/jacoco_20211205_01.PNG" alt="TOURGUIDE IMAGE"></a><br>
 <a href="#"><img width="98%" src="tourguide/asserts/reporting/Junit_test_20211205.PNG" alt="TOURGUIDE IMAGE"></a><br>
 
 Gradle tests - StartLine. <br/>
 <a href="#"><img width="98%" src="tourguide/asserts/reporting/gradle_build_report_20211113_01.PNG" alt="TOURGUIDE IMAGE"></a><br>
 
 // TODO - documentation under progress

## Metrics
TestPerformanceon highVolume User Tracking & User Rewards Computations are performed & available.

<a href="#"><img width="98%" src="tourguide/asserts/reporting/Performance_Location_graph_20211207.PNG" alt="Performance report on User Location"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/Performance_rewards_graph_20211207.PNG" alt="Performance report on User Location"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/Performance_synopsis_20211207-100000_users.PNG" alt="Performance report on User Location"></a><br>
<a href="#"><img width="98%" src="tourguide/asserts/reporting/Performance_unit_tests_20211207_100000_users.PNG" alt="Performance report on User Location"></a><br>

 // TODO - documentation under progress

## Author

**@senthil**