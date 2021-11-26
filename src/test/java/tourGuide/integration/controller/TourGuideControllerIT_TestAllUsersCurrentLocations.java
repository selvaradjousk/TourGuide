package tourGuide.integration.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TourGuideControllerIT_TestAllUsersCurrentLocations {


    @Autowired
    private TestRestTemplate restTemplate;


    @LocalServerPort
    private int port;
    
    private final static String USER_CURRENT_LOCATION_URL = "/getAllCurrentLocations/";
    
	// ##############################################################

	@Test
	public void testGetAllCurrentLocations() {
	
        ResponseEntity<String> response = restTemplate.getForEntity(
        		"http://localhost:"
        		+ port
        		+ USER_CURRENT_LOCATION_URL, String.class);
	
	    assertNotNull(response);
	    

//      assertTrue(response.getBody().contains("longitude"));
//      assertTrue(response.getBody().contains("latitude"));
                  
	
	    }


	//##############################################################

									
}	

