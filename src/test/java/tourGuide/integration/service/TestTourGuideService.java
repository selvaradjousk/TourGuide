package tourGuide.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jsoniter.output.JsonStream;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.User;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tripPricer.Provider;

@DisplayName("IT - Service - TourGuide")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTourGuideService {


	@Autowired
	private GpsUtil gpsUtil;

	@Autowired
	private RewardsService rewardsService;

    @Autowired
    TourGuideService tourGuideService;



	// ##############################################################



	@Test
	public void testGetUserLocation() {

		Locale.setDefault(Locale.US);

		gpsUtil = new GpsUtil();
		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
//		tourGuideService.tracker.stopTracking();

		assertEquals(user.getUserId(), visitedLocation.userId);
		assertEquals(user.getLastVisitedLocation().location.latitude, visitedLocation.location.latitude);
		assertEquals(user.getLastVisitedLocation().location.longitude, visitedLocation.location.longitude);


	}



	// ##############################################################


	
	@Test
	public void testAddUser() {

		Locale.setDefault(Locale.US);

		gpsUtil = new GpsUtil();
		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "testUser2", "000", "testUser2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		tourGuideService.tracker.stopTracking();
		
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}



	// ##############################################################


	
	@Test
	public void testGetAllUsers() {

		Locale.setDefault(Locale.US);

		gpsUtil = new GpsUtil();
		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "testUser2", "000", "testUser2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		List<User> allUsers = tourGuideService.getAllUsers();

		tourGuideService.tracker.stopTracking();
		
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
		assertEquals(2, allUsers.size());
	}



	// ##############################################################

	@Test
	public void testGetAllUsersCurrentLocations() {

		gpsUtil = new GpsUtil();

		rewardsService = new RewardsService(gpsUtil, new RewardCentral());

		InternalTestHelper.setInternalUserNumber(100);

		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		LinkedHashMap<String, Location> allUsersLastLocation
						= new LinkedHashMap<>(tourGuideService.getAllUsersLastLocation());

		String generatedJson = JsonStream.serialize(allUsersLastLocation);

		System.out.println(generatedJson);

		StringBuilder manualJson = new StringBuilder("{");

		allUsersLastLocation.forEach((id, location) ->
				manualJson
					.append("\"")
					.append(id)
					.append("\":")
					.append("{\"longitude\":")
						.append(
								BigDecimal
								.valueOf(location.longitude)
								.setScale(6, RoundingMode.HALF_UP)
								.doubleValue())
					.append(",")
					.append("\"latitude\":")
						.append(
								BigDecimal
								.valueOf(location.latitude)
								.setScale(6, RoundingMode.HALF_UP)
								.doubleValue())
					.append("},")
		);

		manualJson.deleteCharAt(manualJson.length() - 1).append("}");

		System.out.println(manualJson.toString());

		assertThat(manualJson.toString())
				.isEqualToIgnoringWhitespace(generatedJson);
	}


	// ##############################################################
	
	@Test
	public void testTrackUser() {

		Locale.setDefault(Locale.US);

		gpsUtil = new GpsUtil();
		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");

		List<User> users = new ArrayList<>();
		users.add(user);

		int visitedLocationCount = user.getVisitedLocations().size();

		tourGuideService.tracker.startTracking();
		tourGuideService.tracker.trackAndWait(users);
		tourGuideService.tracker.stopTracking();

		assertEquals(visitedLocationCount + 1, user.getVisitedLocations().size());
	}



	// ##############################################################


	
//	@Ignore // Not yet implemented
	@Test
	public void testGetNearbyAttractions() {

		Locale.setDefault(Locale.US);

		gpsUtil = new GpsUtil();
		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
		
		List<Attraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, attractions.size());
	}



	// ##############################################################



	@Test
	public void getTripDeals() {

		Locale.setDefault(Locale.US);

		gpsUtil = new GpsUtil();
		rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "testUser", "000", "testUser@tourGuide.com");

		List<Provider> providers = tourGuideService.getTripDeals(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, providers.size());
	}



	// ##############################################################


    @Test
    public void testUpdateUserPreferences() {
        InternalTestHelper.setInternalUserNumber(0);

        final User user = new User(
        		UUID.randomUUID(),
        		"testUser1",
        		"000",
        		"testUser@tourGuide.com");


        UserPreferencesDTO testUserPreferencesDto = new UserPreferencesDTO(
        		"testUser",
        		999,
        		999,
        		999,
        		999,
        		999,
        		999,
        		999);

        tourGuideService.addUser(user);

        UserPreferencesDTO userPreferencesDto = new UserPreferencesDTO();
        userPreferencesDto.setTripDuration(999);
        userPreferencesDto.setTicketQuantity(999);
        userPreferencesDto.setNumberOfAdults(999);
        userPreferencesDto.setNumberOfChildren(999);
        userPreferencesDto.setCurrency("EUR");
        userPreferencesDto.setHighPricePoint(999);
        userPreferencesDto.setLowerPricePoint(999);
        userPreferencesDto.setAttractionProximity(999);

        // WHEN
        boolean result = tourGuideService
        		.updateUserPreferences(
        				"testUser1",
        				userPreferencesDto);

        // THEN
        assertNotNull(result);
//        assertEquals(true, result);
        assertEquals(testUserPreferencesDto.getTripDuration().intValue(),
        		user.getUserPreferences().getTripDuration());
        assertEquals(testUserPreferencesDto.getTicketQuantity().intValue(),
        		user.getUserPreferences().getTicketQuantity());
        assertEquals(testUserPreferencesDto.getNumberOfAdults().intValue(),
        		user.getUserPreferences().getNumberOfAdults());
        assertEquals(testUserPreferencesDto.getNumberOfChildren().intValue(),
        		user.getUserPreferences().getNumberOfChildren());
        assertEquals("EUR "+ testUserPreferencesDto.getHighPricePoint().intValue(),
        		user.getUserPreferences().getHighPricePoint().toString());
        assertEquals("EUR "+ testUserPreferencesDto.getLowerPricePoint().intValue(),
        		user.getUserPreferences().getLowerPricePoint().toString());
        assertEquals(testUserPreferencesDto.getAttractionProximity().intValue(),
        		user.getUserPreferences().getAttractionProximity());        
        
    }

}
