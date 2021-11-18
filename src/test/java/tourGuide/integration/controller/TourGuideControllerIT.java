package tourGuide.integration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.model.User;
import tourGuide.service.TourGuideService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TourGuideControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TourGuideService tourGuideService;

    @LocalServerPort
    private int port;
    
    private final static String INDEX_URL = "/";
    private final static String USERS_LOCATION_URL = "/getLocation/";
    private final static String NEARBY_ATTRACTIONS_URL = "/getNearbyAttractions/";
    
    
    
	// ##############################################################

	@Test
	public void testIndexPageUrl() {

        ResponseEntity<String> response = restTemplate
        		.getForEntity(
        				"http://localhost:"
        				+ port
        				+ INDEX_URL, String.class);

         assertNotNull(response);
         
         assertEquals("request status",
        		 HttpStatus.OK.value(),
        		 response.getStatusCodeValue());
         
         assertEquals("request body",
        		 "Greetings from TourGuide!",
        		 response.getBody());
         
	    }
	

	// ##############################################################
	
	
	@Test
	public void testGetLocationUrlValid() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USERS_LOCATION_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());
	
	    assertTrue(response.getBody().contains("longitude"));
	    assertTrue(response.getBody().contains("latitude"));
	                     
	
	    }


	//##############################################################



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetLocationUrlWithEmptyUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USERS_LOCATION_URL
						+ "?userName=", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());
                
	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetLocationUrlWithNullValueUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USERS_LOCATION_URL
						+ "?userName=", null);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());
                
	    assertNull(response.getBody());
	
	    }

	// ##############################################################



	@Test
	public void testGetLocationUrlWithMissingParameter() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USERS_LOCATION_URL, String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());

       
	    assertTrue(response.getBody().contains("Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################


	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetLocationUrlWithInvalidUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USERS_LOCATION_URL
						+ "?userName=unkownuser", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());

	    
	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################

	
	@Test
	public void testGetLocationUrlWithUserWithoutVisitedLocationHistory() {

        User user = tourGuideService.getUser("internalUser1");
        user.clearVisitedLocations();

		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USERS_LOCATION_URL
						+ "?userName=internalUser1", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());


	}

	// ##############################################################
	
	
	@Test
	public void testGetNearbyAttractionsValidInput() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
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



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetNearbyAttractionsUrlWithEmptyUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());
                
	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetNearbyAttractionsUrlWithNullValueUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=", null);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());
                
	    assertNull(response.getBody());
	
	    }

	// ##############################################################



	@Test
	public void testGetNearbyAttractionsUrlWithMissingParameter() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL, String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());

       
	    assertTrue(response.getBody().contains("Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################


	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetNearbyAttractionsnUrlWithInvalidUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ NEARBY_ATTRACTIONS_URL
						+ "?userName=unkownuser", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());

	    
	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################

	
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
	    
	    assertEquals("request status",
	    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
	    		response.getStatusCodeValue());

//      assertEquals("request body",
//		 "Greetings from TourGuide!",
//		 response.getBody());

	}

				
}	

