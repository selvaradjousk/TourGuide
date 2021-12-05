package tourGuide.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gpsUtil.GpsUtil;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.UserNotFoundException;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

@DisplayName("IT - Service - TourGuide")
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest("/integration-test.properties")
public class TestTourGuideService {


	@Autowired
	private GpsUtil gpsUtil;

	@Autowired
	private RewardsService rewardsService;

    @Autowired
    TourGuideService tourGuideService;



	// ##############################################################



    @DisplayName("Check <GetUserLocation>"
    		+ " - Given a User,"
    		+ " when GET USER location,"
    		+ " then return USER location")	
	@Test
	public void testGetUserLocation() {

		Locale.setDefault(Locale.US);

    	// GIVEN
        User user = tourGuideService
        		.getUser("internalUser1");

        // WHEN
        LocationDTO result = tourGuideService
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
    	User user = tourGuideService.getUser("internalUser1");

    	Location location = new Location(33.881866, -115.90065);

    	user.getVisitedLocations().clear();

    	VisitedLocation visitedLocation = new VisitedLocation(
    			user.getUserId(),
    			location,
    			new Date());

    	user.addToVisitedLocations(visitedLocation);

    	// WHEN
        LocationDTO result = tourGuideService
        		.getUserLocation("internalUser1");

        // THEN
        assertNotNull(result);
        assertThat(result).isEqualToComparingFieldByField(location);
    }


	// ##############################################################

    @DisplayName("Check <AddUser>"
    		+ " - Given an User with one visited location,"
    		+ " WHEN Requested AddUser,"
    		+ " then return User Added as expected")
    @Test
    public void testAddUser() {

    	// GIVEN
    	UUID userID = UUID.fromString("1851b7bd-737a-4c9d-9c2b-3b5829e417fa");
     	User user = new User(
     			userID,
     			"testUser",
     			"000",
     			"testUser@email.com");

    	// WHEN
    	tourGuideService.addUser(user);

    	// THEN
        assertThat(tourGuideService.getAllUsers()).contains(user);
    }


	// ##############################################################

    @DisplayName("Check <AddUser> - already exists <Exception>"
		+ "GIVEN an Username is already used "
		+ "WHEN Requested AddUser "
		+ "THEN throws DataAlreadyRegisteredException")	
    @Test
    public void testAddUserAlreadyExistsForExceptionThrown() {

    	// GIVEN
    	UUID userID = UUID.fromString("1851b7bd-737a-4c9d-9c2b-3b5829e417fa");
        User user = new User(userID, "internalUser1", "000", "existingUser@email.com");
 
        // THEN  <== WHEN
        assertThrows(DataAlreadyRegisteredException.class, ()
        		-> tourGuideService.addUser(user));
    }
    



	// ##############################################################

    @DisplayName("Check <getAllUsers>"
    		+ " - Given a userlist,"
    		+ " WHEN Requested GET all users,"
    		+ " then return All Users list")
    @Test
    public void testGetAllUsers() {

    	// WHEN
    	List<User> users = tourGuideService
    			.getAllUsers();

    	// THEN
        assertThat(users).isNotEmpty();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    

	// ##############################################################


    @DisplayName("Check <get User>"
    		+ " - Given a User existing,"
    		+ " WHEN Requested GET user,"
    		+ " then return user as expected")
    @Test
    public void testGetUser() {

    	// GIVEN // WHEN
    	User user = tourGuideService
    			.getUser("internalUser1");

    	// THEN
        assertNotNull(user);
        assertEquals("internalUser1", user.getUserName());

    }
    

	// ##############################################################


    @Test
    @DisplayName("Check <get User> - not exists <Exception>"
		+ "GIVEN an Username not exists "
		+ "WHEN Requested GET User "
		+ "THEN throws UserNotFoundException")	
    public void testGetUserNotExistingForUserNotFoundException() {
        
        // THEN  <== WHEN
        assertThrows(UserNotFoundException.class, ()
        		-> tourGuideService
        		.getUser("testUserDoesNotExist"));
        
    }

    

	// ##############################################################

    
    
    @DisplayName("Check <get All Users Locations>"
    		+ " - Given a list of users,"
    		+ " WHEN Requested GET all users locations,"
    		+ " then return users locations as expected")
	@Test
	public void testGetAllUsersCurrentLocations() {

		// GIVEN
        List<User> users = tourGuideService.getAllUsers();

        // WHEN
        Map<String, LocationDTO> result = tourGuideService
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
        tourGuideService.trackUserLocation(user).join();

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
    	UserAttractionRecommendationDTO result = tourGuideService
    			.getUserAttractionRecommendation("internalUser1");

    	// THEN
        assertThat(result.getNearbyAttractions()).isNotEmpty();
        assertEquals(5, result.getNearbyAttractions().size());
    }




	// ##############################################################


    @DisplayName("Check <getTripDeals>"
    		+ " - Given an Username,"
    		+ " WHEN Requested getTripDeals,"
    		+ " then return trip deals as expected")
	@Test
	public void getTripDeals() {

    	// GIVEN
    	User user = tourGuideService.getUser("internalUser1");
        user.setUserPreferences(new UserPreferences(
        		 10,
        		 Money.of(500, Monetary.getCurrency("USD")),
        		 Money.of(1000, Monetary.getCurrency("USD")),
                 5,
                 5,
                 2,
                 3));

         // WHEN
         List<ProviderDTO> result = tourGuideService
        		 .getUserTripDeals("internalUser1");

         // THEN
         assertNotNull(result);
         assertThat(result).isNotEmpty();
         assertEquals(5, result.size());
	}



	// ##############################################################


    @DisplayName("Check <UpdateUserPreferences>"
    		+ " - Given an User's preference,"
    		+ " WHEN Requested UpdateUserPreferences,"
    		+ " then updates user preferences as expected")
    @Test
    public void testUpdateUserPreferences() {
    	

    	// GIVEN
        UserPreferencesDTO userPreferences = new UserPreferencesDTO(
       		 10,
       		 500,
       		 1000,
                5,
                5,
                2,
                3);

        // WHEN
        UserPreferencesDTO result = tourGuideService
        		.updateUserPreferences("internalUser1", userPreferences);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualToComparingFieldByField(userPreferences);
        
 
    }


	// ##############################################################



    @DisplayName("Check <testGetUserRewards>"
    		+ " - Given an User with reward,"
    		+ " WHEN Requested testGetUserRewards,"
    		+ " then return user rewards as expected")
    @Test
    public void testGetUserRewards() {

    	// GIVEN
    	User user = tourGuideService.getUser("internalUser1");
        user.getUserRewards().clear();
        VisitedLocation visitedLocation = new VisitedLocation(
        		user.getUserId(),
        		new Location(30.881866, -115.90065),
        		new Date());
        
        Attraction attraction = new Attraction(
        		UUID.randomUUID(),
        		"Disneyland" ,
        		"Anaheim" ,
                "CA",
                new Location(33.881866, -115.90065));
        
        UserReward userReward = new UserReward(
        		visitedLocation,
        		attraction,
        		1000);
        
        user.addUserReward(userReward);

        // WHEN
        List<UserRewardDTO> result = tourGuideService
        		.getUserRewards("internalUser1");

        // THEN
        assertNotNull(result);
        assertEquals(1000, result.get(0).getRewardPoints());

    }



	// ##############################################################



    @DisplayName("Check <testGetUserRewards> no rewards"
    		+ " - Given an User with no reward,"
    		+ " WHEN Requested testGetUserRewards,"
    		+ " then return user rewards (empty) as expected")
    @Test
    public void testGetUserRewardsWhenNoRewards() {

    	// GIVEN
    	User user = tourGuideService.getUser("internalUser2");
        user.getUserRewards().clear();
        
        // WHEN
        List<UserRewardDTO> result = tourGuideService
        		.getUserRewards("internalUser2");

        // THEN
        assertNotNull(result);
        assertThat(result).isEmpty();
    }



	// ##############################################################

    


    @DisplayName("Check <getTotalRewardPointsForUser>"
    		+ " - Given an Username,"
    		+ " WHEN Requested getTotalRewardPointsForUser,"
    		+ " then return user reward points as expected")
    @Test
    public void getTotalRewardPointsForUser() {

        
    	// WHEN <== // GIVEN
        int result = tourGuideService
        		.getTotalRewardPointsForUser("internalUser1");

        // THEN
        assertNotNull(result);
        assertThat(result > 500);
    }



	// ##############################################################


}
