package tourGuide.integration.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.model.User;
import tourGuide.service.TourGuideService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@TestPropertySource("/integration-test.properties")
public class LocationControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TourGuideService tourGuideService;

    @LocalServerPort
    private int port;
    
    private final static String USER_LOCATION_URL = "/getLocation/";
    private final static String NEARBY_ATTRACTIONS_URL = "/getNearbyAttractions/";
    private final static String USER_CURRENT_LOCATION_URL = "/getAllCurrentLocations/";

    
    

	// ##############################################################
	
    @DisplayName("Check (GetLocation) Valid "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: 200 OK")
	@Test
	public void testGetLocationUrlValid() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_LOCATION_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());
	
	    assertTrue(response.getBody().contains("longitude"));
	    assertTrue(response.getBody().contains("latitude"));
	                     
	
	    }


	//##############################################################



    @DisplayName("Check (GetLocation) EmptyUserName "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: BAD_REQUEST")
	@Test
	public void testGetLocationUrlWithEmptyUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_LOCATION_URL
						+ "?userName=", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
//	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################



    @DisplayName("Check (GetLocation) NullValueUserName "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: BAD_REQUEST")
	@Test
	public void testGetLocationUrlWithNullValueUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_LOCATION_URL
						+ "?userName=", null);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
	    assertNull(response.getBody());
	
	    }

	// ##############################################################


    @DisplayName("Check (GetLocation) missing parameter "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: BAD_REQUEST")
	@Test
	public void testGetLocationUrlWithMissingParameter() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_LOCATION_URL, String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());

       
	    assertTrue(response.getBody().contains("Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################

    @DisplayName("Check (GetLocation) invalid username "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: NOT_FOUND")
	@Test
	public void testGetLocationUrlWithInvalidUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_LOCATION_URL
						+ "?userName=unkownuser", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.NOT_FOUND.value(),
	    		response.getStatusCodeValue());

	    
//	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################

//	@Ignore
	@Test
	public void testGetLocationUrlWithUserWithoutVisitedLocationHistory() {

        User user = tourGuideService.getUser("internalUser1");
        user.clearVisitedLocations();

		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_LOCATION_URL
						+ "?userName=internalUser1", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());


	}

	// ##############################################################
	
    @DisplayName("Check (GetNearbyAttractions)"
    		+ " - Given a request,"
    		+ " when GET GetNearbyAttractions,"
    		+ " then return - Status: 200 OK")
	@Test
	public void testGetNearbyAttractionsValidInput() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());

    

      assertTrue(response.getBody().contains("longitude"));
      assertTrue(response.getBody().contains("latitude"));
      assertTrue(response.getBody().contains("userPosition"));
      assertTrue(response.getBody().contains("nearbyAttractions"));
      assertTrue(response.getBody().contains("rewardPoints"));
      assertTrue(response.getBody().contains("distance"));
	                     
	
	    }


	//##############################################################


    @DisplayName("Check (GetNearbyAttractions) EmptyUserName"
    		+ " - Given a request,"
    		+ " when GET GetNearbyAttractions,"
    		+ " then return - Status: BAD_REQUEST")
	@Test
	public void testGetNearbyAttractionsUrlWithEmptyUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
//	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################



    @DisplayName("Check (GetNearbyAttractions) Null Parameter"
    		+ " - Given a request,"
    		+ " when GET GetNearbyAttractions,"
    		+ " then return - Status: BAD_REQUEST")
	@Test
	public void testGetNearbyAttractionsUrlWithNullValueUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=", null);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
	    assertNull(response.getBody());
	
	    }

	// ##############################################################


    @DisplayName("Check (GetNearbyAttractions) Missing Param "
    		+ " - Given a request,"
    		+ " when GET GetNearbyAttractions,"
    		+ " then return - Status: BAD_REQUEST")
	@Test
	public void testGetNearbyAttractionsUrlWithMissingParameter() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL, String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());

       
	    assertTrue(response.getBody().contains("Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################

    @DisplayName("Check (GetNearbyAttractions) invalid"
    		+ " - Given a request,"
    		+ " when GET GetNearbyAttractions,"
    		+ " then return - Status: NOT_FOUND")
	@Test
	public void testGetNearbyAttractionsnUrlWithInvalidUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=unkownuser", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.NOT_FOUND.value(),
	    		response.getStatusCodeValue());

	    
//	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################

//	@Ignore
	@Test
	public void testGetNearbyAttractionsUrlWithUserWithoutVisitedLocationHistory() {

        User user = tourGuideService.getUser("internalUser1");
        user.clearVisitedLocations();

		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=internalUser1", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());


	}



	// ##############################################################

    @Test
    @DisplayName("Check (testGetAllCurrentLocations)"
    		+ " - Given a request,"
    		+ " when GET testGetAllCurrentLocations,"
    		+ " then return - Status: 200 OK")
	public void testGetAllCurrentLocations() {
	
        ResponseEntity<String> response = restTemplate.getForEntity(
        		"http://localhost:"
        		+ port
        		+ USER_CURRENT_LOCATION_URL, String.class);
	
	    assertNotNull(response);


      assertTrue(response.getBody().contains("longitude"));
      assertTrue(response.getBody().contains("latitude"));

	
	    }


	//##############################################################

									
}	

