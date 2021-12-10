package tourGuide.integration.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import tourGuide.service.GpsLocationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@TestPropertySource("/integration-test.properties")
public class UserControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GpsLocationService tourGuideService;

    @LocalServerPort
    private int port;
    

    private final static String USER_PREFERENCES_URL = "/updateUserPreferences/";
    
    

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


    @DisplayName("Check (GetUser) "
    		+ " - Given a request,"
    		+ " when GET GetUser,"
    		+ " then return - Status: 200 OK")
	@Test
	public void testGetUser() {
	
		ResponseEntity<String> response = restTemplate
				.getForEntity(
						"http://localhost:"
						+ port
						+ "/getUser"
						+ "?userName=internalUser6", String.class);
	
	    assertNotNull(response);
	    
	    assertEquals(
	    		HttpStatus.OK.value(),
	    		response.getStatusCodeValue());
	
	    }


	//##############################################################

									
}	

