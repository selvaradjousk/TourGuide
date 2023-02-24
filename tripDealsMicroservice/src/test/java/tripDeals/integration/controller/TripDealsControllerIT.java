package tripDeals.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TripDealsControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
	// URL
    
	private final static String PROVIDERS_URL = "/tripDeals/providers/test-server-api-key/9732e7e3-943e-4169-97e1-6f171b0e86f0/999/999/999/999";

	
	private final static String PROVIDERS_URL_INVALID_EMPTY_PARAM = "/tripDeals/providers/test-server-api-key/9732e7e3-943e-4169-97e1-6f171b0e86f0/999/ /999/999";


	//###############################################################


    
	@Test
    @DisplayName("Check (getProviders) "
    		+ " - Given an User Preferences,"
    		+ " when GET getProviders,"
    		+ " then return - response OK")
    public void testGetProviders() throws Exception {
		
        ResponseEntity<Object[]> response = restTemplate
        		.getForEntity("http://localhost:" + port +
                PROVIDERS_URL, Object[].class);

        assertThat(response.getBody().length).isGreaterThan(0);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}



	//###############################################################



    @DisplayName("Check (getProviders) empty provider list"
    		+ " - Given an User Preferences, empty provider list "
    		+ " when GET getProviders,"
    		+ " then return - response NOT_FOUND")
	@Test
	public void getProvidersRequestEmptyList() throws Exception {

		
        ResponseEntity<Object[]> response = restTemplate
        		.getForEntity("http://localhost:" + port +
        				PROVIDERS_URL_INVALID_EMPTY_PARAM, null);

        assertEquals( HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
	}



	//###############################################################


    
}
