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


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@TestPropertySource("/integration-test.properties")
public class TripDealsControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;


//    @Autowired
//    private TourGuideService tourGuideService;

    @LocalServerPort
    private int port;
    

    private final static String USER_TRIP_DEALS_URL = "/getTripDeals/";



	//##############################################################
	
    @DisplayName("Check (GetTripDeals)"
    		+ " - Given an username,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: 200 OK")
	@Test
	public void testGetTripDealsUrlValid() {
    	
//        User user = tourGuideService.getUser("internalUser6");
	
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

	                     
	
	    }


	//##############################################################



    @DisplayName("Check (GetTripDeals) empty username"
    		+ " - Given an empty username,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: BAD_REQUEST")
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
                
	
	    }

	// ##############################################################



    @DisplayName("Check (GetTripDeals) null value username"
    		+ " - Given an username is null,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: BAD_REQUEST")
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


    @DisplayName("Check (GetTripDeals) username WithMissingParameter"
    		+ " - Given an username WithMissingParameter,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: BAD_REQUEST")
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

       
	    assertTrue(response.getBody().contains(
	    		"Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################



    @DisplayName("Check (GetTripDeals) invalid username"
    		+ " - Given an username invalid,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: BAD_REQUEST")
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
					
}	

