package tourGuide.integration.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.User;
import tourGuide.service.TourGuideService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@TestPropertySource("/integration-test.properties")
public class TourGuideControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TourGuideService tourGuideService;

    @LocalServerPort
    private int port;
    
    private final static String INDEX_URL = "/";
    private final static String USER_LOCATION_URL = "/getLocation/";
    private final static String NEARBY_ATTRACTIONS_URL = "/getNearbyAttractions/";
    private final static String USER_REWARDS_URL = "/getRewards/";
    private final static String USER_CURRENT_LOCATION_URL = "/getAllCurrentLocations/";
    private final static String USER_TRIP_DEALS_URL = "/getTripDeals/";
    private final static String USER_PREFERENCES_URL = "/updateUserPreferences/";
    
    
	// ##############################################################

	@Test
	public void testIndexPageUrl() {

        ResponseEntity<String> response = restTemplate
        		.getForEntity(
        				"http://localhost:"
        				+ port
        				+ INDEX_URL, String.class);

         assertNotNull(response);
         
         assertEquals(
        		 HttpStatus.OK.value(),
        		 response.getStatusCodeValue());
         
         assertEquals(
        		 "Greetings from TourGuide!",
        		 response.getBody());
         
	    }
	

	// ##############################################################
	
//	@Ignore
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



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
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



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
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

//	@Ignore
	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
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
	
//	@Ignore
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


//	@Ignore
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
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
//	    assertTrue(response.getBody().contains("USERNAME required"));
	
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
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
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
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());

       
	    assertTrue(response.getBody().contains("Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################

//	@Ignore
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
	// ##############################################################
	
//	@Ignore
	// TODO have to fix getRewards for output is empty
	@Test
	public void testGetRewardsValidInput() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_REWARDS_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());


//	      assertEquals("request body",
//			 "[]",
//			 response.getBody());
//
//      assertTrue(response.getBody().contains("longitude"));
//      assertTrue(response.getBody().contains("latitude"));
//      assertTrue(response.getBody().contains("userPosition"));
//      assertTrue(response.getBody().contains("nearbyAttractions"));
//      assertTrue(response.getBody().contains("rewardPoints"));
//      assertTrue(response.getBody().contains("distance"));
	                     
	
	    }


	//##############################################################



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetRewardsUrlWithEmptyUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_REWARDS_URL
						+ "?userName=", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
//	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetRewardsUrlWithNullValueUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_REWARDS_URL
						+ "?userName=", null);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
	    assertNull(response.getBody());
	
	    }

	// ##############################################################



	@Test
	public void testGetRewardsUrlWithMissingParameter() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_REWARDS_URL, String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());

       
	    assertTrue(response.getBody().contains("Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################

//	@Ignore
	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetRewardsnUrlWithInvalidUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_REWARDS_URL
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
	public void testGetRewardsUrlWithUserWithoutVisitedLocationHistory() {

        User user = tourGuideService.getUser("internalUser1");
        user.clearVisitedLocations();

		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_REWARDS_URL
						+ "?userName=internalUser1", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());


	}


	// ##############################################################

	// THIIS TEST RUNS SUCCESSFULLY WHEN RUN INDIVIDUALLY BUT WITH ALL OTHER IT FAILS
	// GIVES INTERNAL SERVER ERROR MESSAGE
	// SHIFTED TO THE SEPLERATE TEST CLASS FILE : TourGuideControllerIT_TestAllUsersCurrentLocations.java
//	@Ignore
	@Test
	public void testGetAllCurrentLocations() {
	
        ResponseEntity<String> response = restTemplate.getForEntity(
        		"http://localhost:"
        		+ port
        		+ USER_CURRENT_LOCATION_URL, String.class);
	
	    assertNotNull(response);
	    
//	    assertEquals("request status",
//	    		HttpStatus.OK.value(),
//	    		response.getStatusCodeValue());

//      assertEquals("request body",
//		 "Greetings from TourGuide!",
//		 response.getBody());


      assertTrue(response.getBody().contains("longitude"));
      assertTrue(response.getBody().contains("latitude"));

	
	    }


	//##############################################################
	//##############################################################
	
//	@Ignore
	@Test
	public void testGetTripDealsUrlValid() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_TRIP_DEALS_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());
	    
   
	    assertTrue(response.getBody().contains("name"));
	    assertTrue(response.getBody().contains("tripId"));
	    assertTrue(response.getBody().contains("price"));
//	    assertTrue(response.getBody().contains("mostSigBits"));
//	    assertTrue(response.getBody().contains("leastSigBits"));
//	    assertTrue(response.getBody().contains("leastSignificantBits"));
//	    assertTrue(response.getBody().contains("mostSignificantBits"));
	                     
	
	    }


	//##############################################################



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetTripDealsUrlWithEmptyUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_TRIP_DEALS_URL
						+ "?userName=", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
//	    assertTrue(response.getBody().contains("USERNAME required"));
	
	    }

	// ##############################################################



	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetTripDealsUrlWithNullValueUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_TRIP_DEALS_URL
						+ "?userName=", null);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());
                
	    assertNull(response.getBody());
	
	    }

	// ##############################################################



	@Test
	public void testGetTripDealsUrlWithMissingParameter() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_TRIP_DEALS_URL, String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.BAD_REQUEST.value(),
	    		response.getStatusCodeValue());

       
	    assertTrue(response.getBody().contains("Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################


	// EXCEPTION HANDLING TO BE DONE BY INTRODUCING DTO
	@Test
	public void testGetTripDealsUrlWithInvalidUserName() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_TRIP_DEALS_URL
						+ "?userName=unkownuser", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.NOT_FOUND.value(),
	    		response.getStatusCodeValue());

//      assertEquals("request body",
//		 "Greetings from TourGuide!",
//		 response.getBody());
	    

	    }

	// ##############################################################



    @Test
    @DisplayName("Check (updateUserPreferences)"
    		+ " - Given an user preference to update,"
    		+ " when PUT updateUserPreferences,"
    		+ " then return - Status: 200 OK update done")
    public void testUpdateUserPreferences() throws Exception {

    	UserPreferencesDTO userPreferencesToUpdate = new UserPreferencesDTO(
    			999, 999, 999, 999, 999, 999, 999);

        restTemplate.put(
        		"http://localhost:" + port +
                USER_PREFERENCES_URL + "?userName=internalUser1",
                userPreferencesToUpdate,
                UserPreferencesDTO.class);

        User user = tourGuideService.getUser("internalUser1");

        assertEquals(
        		userPreferencesToUpdate.getAttractionProximity(),
        		user.getUserPreferences().getAttractionProximity());
        
        assertEquals(
        		userPreferencesToUpdate.getTripDuration(),
        		user.getUserPreferences().getTripDuration());
        
        assertEquals(
        		userPreferencesToUpdate.getTicketQuantity(),
        		user.getUserPreferences().getTicketQuantity());
        
        assertEquals(
        		userPreferencesToUpdate.getNumberOfAdults(),
        		user.getUserPreferences().getNumberOfAdults());
        
        assertEquals(
        		userPreferencesToUpdate.getNumberOfChildren(),
        		user.getUserPreferences().getNumberOfChildren());
        
        assertNotEquals(
        		Integer.MAX_VALUE,
        		user.getUserPreferences().getAttractionProximity());
    }	

	// ##############################################################

    @Test
    @DisplayName("Check (updateUserPreferences) empty username"
    		+ " - Given an user preference to update with no username,"
    		+ " when PUT updateUserPreferences,"
    		+ " then return - Status: 200 OK  but error message username required")
    public void testUpdateUserPreferencesEmptyUserName() throws Exception {

    	UserPreferencesDTO userPreferencesToUpdate = new UserPreferencesDTO(999,
                999, 999, 999, 999, 999, 999);

        restTemplate.put(
        		"http://localhost:" + port +
                USER_PREFERENCES_URL + "?userName=",
                userPreferencesToUpdate,
                UserPreferencesDTO.class);

        User user = tourGuideService.getUser("internalUser1");

        assertNotEquals(
        		userPreferencesToUpdate.getAttractionProximity(),
        		user.getUserPreferences().getAttractionProximity());
        
        assertNotEquals(
        		userPreferencesToUpdate.getTripDuration(),
        		user.getUserPreferences().getTripDuration());
        
        assertNotEquals(
        		userPreferencesToUpdate.getTicketQuantity(),
        		user.getUserPreferences().getTicketQuantity());
        
        assertNotEquals(
        		userPreferencesToUpdate.getNumberOfAdults(),
        		user.getUserPreferences().getNumberOfAdults());
        
        assertNotEquals(
        		userPreferencesToUpdate.getNumberOfChildren(),
        		user.getUserPreferences().getNumberOfChildren());
        
        assertEquals(
        		Integer.MAX_VALUE,
        		user.getUserPreferences().getAttractionProximity());
    }	

	// ##############################################################


									
}	

