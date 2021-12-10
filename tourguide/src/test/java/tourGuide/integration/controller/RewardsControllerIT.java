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
import tourGuide.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@TestPropertySource("/integration-test.properties")
public class RewardsControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;
    

    private final static String USER_REWARDS_URL = "/getRewards/";

    

	// ##############################################################
	
    @Test
    @DisplayName("Check (GetRewards) ValidInput "
    		+ " - Given a request,"
    		+ " when GET GetRewards,"
    		+ " then return - Status: 200 OK")
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

                    
	
	    }


	//##############################################################


    @DisplayName("Check (GetRewards) empty username "
    		+ " - Given a request,"
    		+ " when GET GetRewards,"
    		+ " then return - Status: BAD_REQUEST")
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


    @DisplayName("Check (GetRewards) null value input "
    		+ " - Given a request,"
    		+ " when GET GetRewards,"
    		+ " then return - Status: BAD_REQUEST")
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


    @DisplayName("Check (GetRewards) missing param "
    		+ " - Given a request,"
    		+ " when GET GetRewards,"
    		+ " then return - Status: BAD_REQUEST")
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

       
	    assertTrue(response.getBody().contains(
	    		"Required String parameter 'userName' is not present"));
	
	    }

	// ##############################################################

    @DisplayName("Check (GetRewards) invalid username "
    		+ " - Given a request,"
    		+ " when GET GetRewards,"
    		+ " then return - Status: NOT_FOUND")
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

	    

	
	    }

	// ##############################################################


    @DisplayName("Check (GetRewards) for username without visited location history"
    		+ " - Given a request,"
    		+ " when GET GetRewards,"
    		+ " then return - Status: 200 OK")
	@Test
	public void testGetRewardsUrlWithUserWithoutVisitedLocationHistory() {

        User user = userService.getUser("internalUser1");
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


	//##############################################################

									
}	

