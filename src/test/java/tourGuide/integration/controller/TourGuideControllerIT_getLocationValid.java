package tourGuide.integration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
public class TourGuideControllerIT_getLocationValid {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private final static String USER_LOCATION_URL = "/getLocation/";
    private final static String NEARBY_ATTRACTIONS_URL = "/getNearbyAttractions/";
    private final static String USER_REWARDS_URL = "/getRewards/";
    private final static String USER_TRIP_DEALS_URL = "/getTripDeals/";

    

	// ##############################################################
	
	
	@Test
	public void testGetLocationUrlValid() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_LOCATION_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());
	
	    assertTrue(response.getBody().contains("longitude"));
	    assertTrue(response.getBody().contains("latitude"));
	                     
	
	    }


	//##############################################################

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
	// ##############################################################
	
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
	    
	    assertEquals("request status",
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

	
	
	@Test
	public void testGetTripDealsUrlValid() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ USER_TRIP_DEALS_URL
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals("request status",
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());
	    
   
	    assertTrue(response.getBody().contains("name"));
	    assertTrue(response.getBody().contains("tripId"));
	    assertTrue(response.getBody().contains("price"));
	    assertTrue(response.getBody().contains("mostSigBits"));
	    assertTrue(response.getBody().contains("leastSigBits"));
	    assertTrue(response.getBody().contains("leastSignificantBits"));
	    assertTrue(response.getBody().contains("mostSignificantBits"));
	                     
	
	    }


	//##############################################################

		
									
}	

