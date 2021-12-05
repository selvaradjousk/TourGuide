package tourGuide.integration.controller;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@TestPropertySource("/integration-test.properties")
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

