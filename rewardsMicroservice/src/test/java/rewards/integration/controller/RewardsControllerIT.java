package rewards.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("IT - Controller - Rewards - Microservice")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardsControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    
    private final static String REWARDS_POINTS_URL = "/rewards/points/";

    private UUID userId;

    private UUID attractionId;
    
    
	//###############################################################

    
    @BeforeEach
    public void setUp() {

        userId = UUID.fromString("9732e7e3-943e-4169-97e1-6f171b0e86f0");
        attractionId = UUID.fromString("cb2df5f1-0305-4f43-ad12-c664238cf411");

    }


	//###############################################################


    @Test
    @DisplayName("Check (getAttractionRewardPoints) "
    		+ " - Given an user & attraction id,"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: 200 OK")
     public void testGetAttractionRewardPoints() throws Exception {

        ResponseEntity<Integer> response = restTemplate.getForEntity(
        		"http://localhost:" + port + REWARDS_POINTS_URL
                + userId + "/" + attractionId, Integer.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

    }




	//###############################################################

 
    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) without userId "
    		+ " - Given an user & attraction id, without user Id"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsWithoutUserId() throws Exception {


        ResponseEntity<Integer> response = restTemplate.getForEntity(
        		"http://localhost:" + port + REWARDS_POINTS_URL
                + 0 + "/" + attractionId, null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        
    }

	//###############################################################


    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) without attraction "
    		+ " - Given an user & attraction id, without attraction Id"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsWithoutAttractionId() throws Exception {


        ResponseEntity<Integer> response = restTemplate.getForEntity(
        		"http://localhost:" + port + REWARDS_POINTS_URL
                + userId + "/" + 0, null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());

    }
    


	//###############################################################


    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) invalid userId "
    		+ " - Given an user & attraction id, invalid user Id"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsWithinvalidUserId() throws Exception {


        ResponseEntity<Integer> response = restTemplate.getForEntity(
        		"http://localhost:" + port + REWARDS_POINTS_URL
                + "fdsfsqdf" + "/" + attractionId, null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());

    }
    

	//###############################################################


    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) invalid attractionId "
    		+ " - Given an user & attraction id, invalid attractionId"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsWithinvalidAttractionId() throws Exception {


        ResponseEntity<Integer> response = restTemplate.getForEntity(
        		"http://localhost:" + port + REWARDS_POINTS_URL
                + userId + "/" + "fsqdfsqdf", null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());

    }
    

	//###############################################################
   
   
    
}
