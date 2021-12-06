package gps.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.exception.UserNotFoundException;
import gps.service.GpsService;

@DisplayName("IT - Service - Gps Microservice")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class GpsServiceIT {


	@Autowired
    private GpsService gpsService;



    private static UUID userID;


	// ##############################################################


    @BeforeEach
    public void setUp() {

    	userID = UUID.fromString("6551af94-3263-4253-8600-e9add493f508");



    }



	// ##############################################################


    @DisplayName("Check <GetUserLocation>"
    		+ " - Given a UseID,"
    		+ " when GET USER location,"
    		+ " then return USER expected location match with VisitedLocationDTO")
    @Test
	public void testGetUserLocation() {


        VisitedLocationDTO result = gpsService.getUserLocation(userID);

        assertNotNull(result);
        assertNotNull(result.getLocation().getLongitude());

    }



	// ##############################################################

    @DisplayName("Check <GetUserLocation> user Null"
    		+ " - Given a UseID, null"
    		+ " when GET USER location,"
    		+ " then return Exception thrown")
    @Test
	public void testGetUserLocationUserIdNullValue() {


       assertThrows(UserNotFoundException.class, ()
        		-> gpsService.getUserLocation(null));
    }



	// ##############################################################

    @DisplayName("Check <get User Attraction "
    		+ " - Given an User list,"
    		+ " WHEN Requested getUserAttractionRecommendation,"
    		+ " then return Attractions list as expected")
    @Test
    public void testGetAttractions() {

       
        List<AttractionDTO> attractionList = gpsService.getAttractions();

        List<AttractionDTO> result = gpsService
        		.getAttractions();

        assertNotNull(result);
        assertEquals(attractionList.size(), result.size());
        assertTrue(result.size() > 0);

    }



	// ##############################################################


}
