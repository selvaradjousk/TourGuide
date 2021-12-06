package gps.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gps.dto.VisitedLocationDTO;

@DisplayName("IT - Controller - GPS - Microservice")
@ExtendWith(SpringExtension.class)
class GpsControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URL
    private final static String ATTRACTIONS_URL = "/gps/attractions";

    private final static String USER_LOCATION_URL = "/gps/userLocation/";


	//###############################################################


	
    @BeforeEach
    public void setUp() {

    }


	//###############################################################


	

    @Test
    @DisplayName("Check (getUserLocation) "
    		+ " - Given an userId,"
    		+ " when GET getUserLocation URL,"
    		+ " then return - Status: 200 OK")
     public void testGetUserLocationRequest() throws Exception {

        ResponseEntity<VisitedLocationDTO> response = restTemplate
        		.getForEntity(
        		"http://localhost:" + port +
                USER_LOCATION_URL + "6551af94-3263-4253-8600-e9add493f508",
                VisitedLocationDTO.class);


        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }



	//###############################################################


	

    @Test
    @DisplayName("Check (getUserLocation) with empty input"
    		+ " - Given an userId empty,"
    		+ " when GET getUserLocation URL,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetUserLocationRequestWithEmptyInput() throws Exception {

        ResponseEntity<VisitedLocationDTO> response = restTemplate
        		.getForEntity(
        		"http://localhost:" + port +
                USER_LOCATION_URL + "",
                VisitedLocationDTO.class);


        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        
        
    }





	//###############################################################

	

    @Test
    @DisplayName("Check (getUserLocation) with Null input"
    		+ " - Given an userId null,"
    		+ " when GET getUserLocation URL,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetUserLocationRequestWithNullInput() throws Exception {

        ResponseEntity<VisitedLocationDTO> response = restTemplate
        		.getForEntity(
        		"http://localhost:" + port +
                USER_LOCATION_URL + null,
                VisitedLocationDTO.class);


        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }





	//###############################################################



    @Test
    @DisplayName("Check (getUserLocation) with invalid input"
    		+ " - Given an userId invalid,"
    		+ " when GET getUserLocation URL,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetUserLocationRequestWithinvalideInput() throws Exception {

        ResponseEntity<VisitedLocationDTO> response = restTemplate
        		.getForEntity(
        		"http://localhost:" + port +
                USER_LOCATION_URL + "sqdhfqskh",
                VisitedLocationDTO.class);


        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }





	//###############################################################

    @Test
    @DisplayName("Check (getAttractions)"
    		+ " - Given an attraction list,"
    		+ " when GET getAttractions URL,"
    		+ " then return - Status: 200 OK")
    public void testGetAttractionsRequest() throws Exception {

        ResponseEntity<Object[]> response = restTemplate
        		.getForEntity(
        				"http://localhost:"
		        		+ port +
		                ATTRACTIONS_URL,
		                Object[].class);

        assertNotNull(response);
        assertThat(response.getBody().length).isGreaterThan(0);
        assertEquals( HttpStatus.OK.value(), response.getStatusCodeValue());
    }
    


	//###############################################################



		
}
