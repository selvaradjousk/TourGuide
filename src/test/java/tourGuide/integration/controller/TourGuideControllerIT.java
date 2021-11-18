package tourGuide.integration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
@ActiveProfiles("test")
public class TourGuideControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private final static String INDEX_URL = "/";


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
	
}
