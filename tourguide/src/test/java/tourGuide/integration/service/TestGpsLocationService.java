package tourGuide.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.LocationDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.GpsLocationService;
import tourGuide.service.RewardsService;
import tourGuide.service.UserService;

@DisplayName("IT - Service - GpsLocation")
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest("/integration-test.properties")
public class TestGpsLocationService {


//	@Autowired
//	private GpsUtil gpsUtil;

	@Autowired
	private GpsLocationService gpsLocationService;

    @Autowired
    UserService userService;

    @Autowired
    RewardsService rewardService;   
 


	// ##############################################################



    @DisplayName("Check <GetUserLocation>"
    		+ " - Given a User,"
    		+ " when GET USER location,"
    		+ " then return USER location")	
	@Test
	public void testGetUserLocation() {

		Locale.setDefault(Locale.US);

    	// GIVEN
        User user = userService
        		.getUser("internalUser1");

        // WHEN
        LocationDTO result = gpsLocationService
        		.getUserLocation("internalUser1");

        // THEN
        assertNotNull(result);
		assertEquals(user.getLastVisitedLocation().getLocation().getLatitude(), result.getLatitude());
		assertEquals(user.getLastVisitedLocation().getLocation().getLongitude(), result.getLongitude());


	}



	// ##############################################################


    @DisplayName("Check <GetUserLocation>"
    		+ " - Given a User with one visited location,"
    		+ " when GET USER location,"
    		+ " then return USER expected location")	
    public void testUserLocationWithVisitedLocation() {

    	// GIVEN
    	User user = userService.getUser("internalUser1");

    	Location location = new Location(33.881866, -115.90065);

    	user.getVisitedLocations().clear();

    	VisitedLocation visitedLocation = new VisitedLocation(
    			user.getUserId(),
    			location,
    			new Date());

    	user.addToVisitedLocations(visitedLocation);

    	// WHEN
        LocationDTO result = gpsLocationService
        		.getUserLocation("internalUser1");

        // THEN
        assertNotNull(result);
        assertThat(result).isEqualToComparingFieldByField(location);
    }



	// ##############################################################

    
    
    @DisplayName("Check <get All Users Locations>"
    		+ " - Given a list of users,"
    		+ " WHEN Requested GET all users locations,"
    		+ " then return users locations as expected")
	@Test
	public void testGetAllUsersCurrentLocations() {

		// GIVEN
        List<User> users = userService.getAllUsers();

        // WHEN
        Map<String, LocationDTO> result = gpsLocationService
        		.getAllUserRecentLocation();

        // THEN
        assertNotNull(users);
        assertNotNull(result);
        result.values().forEach(r -> assertNotNull(r));
        assertEquals(users.size(), result.size());
        
	}



	// ##############################################################
	

	
    @DisplayName("Check <get track User>"
    		+ " - Given an User,"
    		+ " WHEN Requested track User Location,"
    		+ " then return location and adds to history as expected")
    @Test
    public void testTrackUser() {

    	// GIVEN
    	UUID userID = UUID.randomUUID();
        User user = new User(
        		userID,
        		"internalUser1",
        		"000",
        		"testUser@email.com");

        // WHEN
        gpsLocationService.trackUserLocation(user).join();

        // THEN
        assertEquals(1, user.getVisitedLocations().size());
    }
    


	// ##############################################################
	

    @DisplayName("Check <get User Attraction Recommendation>"
    		+ " - Given an User,"
    		+ " WHEN Requested getUserAttractionRecommendation,"
    		+ " then return 5 nearby Attractions as expected")
    @Test
    public void testGetUserAttractionRecommendation() {

    	// WHEN <== // GIVEN
    	UserAttractionRecommendationDTO result = gpsLocationService
    			.getUserAttractionRecommendation("internalUser1");

    	// THEN
        assertThat(result.getNearbyAttractions()).isNotEmpty();
        assertEquals(5, result.getNearbyAttractions().size());
    }





	// ##############################################################

    


    @DisplayName("Check <getTotalRewardPointsForUser>"
    		+ " - Given an Username,"
    		+ " WHEN Requested getTotalRewardPointsForUser,"
    		+ " then return user reward points as expected")
    @Test
    public void getTotalRewardPointsForUser() {

        
    	// WHEN <== // GIVEN
        int result = gpsLocationService
        		.getTotalRewardPointsForUser("internalUser1");

        // THEN
        assertNotNull(result);
        assertThat(result > 500);
    }



	// ##############################################################


}
