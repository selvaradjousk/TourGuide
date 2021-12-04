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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import gpsUtil.GpsUtil;
import tourGuide.dto.LocationDTO;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.UserNotFoundException;
import tourGuide.model.Location;
import tourGuide.model.User;
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

    	Location location = new Location(-160.326003, -73.869629);

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
	

	
	
	//    @Ignore
//	@Test
//	public void testTrackUser() {
//
//		Locale.setDefault(Locale.US);
//
//		gpsUtil = new GpsUtil();
//		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		InternalTestHelper.setInternalUserNumber(0);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//		
//		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");
//
//		List<User> users = new ArrayList<>();
//		users.add(user);
//
//		int visitedLocationCount = user.getVisitedLocations().size();
//
//		tourGuideService.tracker.startTracking();
//		tourGuideService.tracker.trackAndWait(users);
//		tourGuideService.tracker.stopTracking();
//
//		assertEquals(visitedLocationCount + 1, user.getVisitedLocations().size());
//	}
//
//
//
//	// ##############################################################
//
//
//	
//	@Ignore
//	@Test
//	public void testGetNearbyAttractions() {
//
//		Locale.setDefault(Locale.US);
//
//		gpsUtil = new GpsUtil();
//		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		InternalTestHelper.setInternalUserNumber(0);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//		
//		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");
//		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
//		
//		List<Attraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);
//		
//		tourGuideService.tracker.stopTracking();
//		
//		assertEquals(5, attractions.size());
//	}
//
//
//
//	// ##############################################################
//
//
//    @Ignore
//	@Test
//	public void getTripDeals() {
//
//		Locale.setDefault(Locale.US);
//
//		gpsUtil = new GpsUtil();
//		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		InternalTestHelper.setInternalUserNumber(0);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//		
//		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");
//
//		List<Provider> providers = tourGuideService.getTripDeals(user);
//		
//		tourGuideService.tracker.stopTracking();
//		
//		assertEquals(5, providers.size());
//	}
//
//
//
//	// ##############################################################
//
//
//    @Test
//    public void testUpdateUserPreferences() {
//        InternalTestHelper.setInternalUserNumber(0);
//
//        final User user = new User(
//        		UUID.randomUUID(),
//        		"testUser1",
//        		"000",
//        		"testUser@tourGuide.com");
//
//
//        UserPreferencesDTO testUserPreferencesDto = new UserPreferencesDTO(
//        		"testUser",
//        		999,
//        		999,
//        		999,
//        		999,
//        		999,
//        		999,
//        		999);
//
//        tourGuideService.addUser(user);
//
//        UserPreferencesDTO userPreferencesDto = new UserPreferencesDTO();
//        userPreferencesDto.setTripDuration(999);
//        userPreferencesDto.setTicketQuantity(999);
//        userPreferencesDto.setNumberOfAdults(999);
//        userPreferencesDto.setNumberOfChildren(999);
//        userPreferencesDto.setCurrency("EUR");
//        userPreferencesDto.setHighPricePoint(999);
//        userPreferencesDto.setLowerPricePoint(999);
//        userPreferencesDto.setAttractionProximity(999);
//
//        // WHEN
//        boolean result = tourGuideService
//        		.updateUserPreferences(
//        				"testUser1",
//        				userPreferencesDto);
//
//        // THEN
//        assertNotNull(result);
////        assertEquals(true, result);
//        assertEquals(testUserPreferencesDto.getTripDuration().intValue(),
//        		user.getUserPreferences().getTripDuration());
//        assertEquals(testUserPreferencesDto.getTicketQuantity().intValue(),
//        		user.getUserPreferences().getTicketQuantity());
//        assertEquals(testUserPreferencesDto.getNumberOfAdults().intValue(),
//        		user.getUserPreferences().getNumberOfAdults());
//        assertEquals(testUserPreferencesDto.getNumberOfChildren().intValue(),
//        		user.getUserPreferences().getNumberOfChildren());
//        assertEquals("EUR "+ testUserPreferencesDto.getHighPricePoint().intValue(),
//        		user.getUserPreferences().getHighPricePoint().toString());
//        assertEquals("EUR "+ testUserPreferencesDto.getLowerPricePoint().intValue(),
//        		user.getUserPreferences().getLowerPricePoint().toString());
//        assertEquals(testUserPreferencesDto.getAttractionProximity().intValue(),
//        		user.getUserPreferences().getAttractionProximity());        
//        
//    }

}
