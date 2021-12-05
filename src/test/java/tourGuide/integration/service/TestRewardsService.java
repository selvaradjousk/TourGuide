package tourGuide.integration.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.AttractionDTO;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.service.GpsUtilMicroService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.util.AttractionMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/integration-test.properties")
public class TestRewardsService {


    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private TourGuideService tourGuideService;

    @Autowired
    private GpsUtilMicroService gpsUtilMicroService;

    @Autowired
    private AttractionMapper attractionMapper;

	// ##############################################################



    @DisplayName("Check <testUserGetRewards>"
    		+ " - Given a User with specifically one visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return one rewards as expected")	
	@Test
	public void testUserGetRewards() throws InterruptedException {

    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		AttractionDTO attraction = gpsUtilMicroService
				.getAttractions().get(0);

		user.addToVisitedLocations(new VisitedLocation(
				user.getUserId(),
				attraction.getLocation(),
				new Date()));


		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 // THEN
		assertEquals(1, user.getUserRewards().size());
	}



	// ##############################################################



    @DisplayName("Check <testUserGetRewards> No visited location"
    		+ " - Given a User with specifically No visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return No rewards as expected")	
	@Test
	public void testUserGetRewardsWithNoVisitedAttraction() throws InterruptedException {

    	
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 // THEN
		assertEquals(0, user.getUserRewards().size());
	}



	// ##############################################################




    @DisplayName("Check <testUserGetRewards> on revisiting Same location"
    		+ " - Given a User with revisiting one same visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return one rewards as expected")	
	@Test
	public void testUserGetRewardsOnRevisitingSameLocation() throws InterruptedException {

    	
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		AttractionDTO attraction = gpsUtilMicroService
				.getAttractions().get(0);

		VisitedLocation visitedLocation1 = new VisitedLocation(
												user.getUserId(),
												attraction.getLocation(),
												new Date());


		VisitedLocation visitedLocation2 = new VisitedLocation(
												user.getUserId(),
												attraction.getLocation(),
												new Date());

        user.addToVisitedLocations(visitedLocation1);
        user.addToVisitedLocations(visitedLocation2);
        user.addUserReward(new UserReward(
        		visitedLocation1, attractionMapper
        		.toAttraction(attraction), 500));
		
		assertEquals(1, user.getUserRewards().size());
		
		
		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 
		 // THEN
		assertEquals(1, user.getUserRewards().size());
	}



	// ##############################################################




    @DisplayName("Check <testUserGetRewards> For No Attractions Near VisitedLocation"
    		+ " - Given a User with specifically one visited locaiton with no attraction nearby,"
    		+ " when Calculate rewards,"
    		+ " then return no rewards as expected")	
	@Test
	public void testUserGetRewardsForNoAttractionsNearVisitedLocation() throws InterruptedException {

    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		gpsUtilMicroService
				.getAttractions()
				.get(0)
				.setLocation(new Location(30.881866, -115.90065));

        VisitedLocation visitedLocation = new VisitedLocation(
        		user.getUserId(),
        		new Location(-30.881866, -115.90065),
        		new Date());
		
        user.addToVisitedLocations(visitedLocation);

		assertEquals(0, user.getUserRewards().size());

		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 // THEN
		assertEquals(0, user.getUserRewards().size());
	}



	// ##############################################################





			
////	@Test
////	public void isWithinAttractionProximity() {
////
////		Locale.setDefault(Locale.US);
////
//////		GpsUtil gpsUtil = new GpsUtil();
//////		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
////		AttractionDTO attraction = gpsUtilMicroService.getAttractions().get(0);
////		assertTrue(rewardsService.isNearAttraction(attraction, attraction));
////	}
//
//
//
//	// ##############################################################
//
//		
////	@Ignore // Needs fixed - can throw ConcurrentModificationException
//	@Test
//	public void nearAllAttractions() {
//
//		// GpsUtil gpsUtil = new GpsUtil();
//		// RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
////		rewardsService.setProximityBuffer(Integer.MAX_VALUE);
//
////		InternalTestHelper.setInternalUserNumber(1);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtilMicroService, null, null, rewardsService, null, null, null, null, null, null, null, null);
//		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");	
//		List<User> users = new ArrayList<>();
//
//		users.add(user);
//		tracker.trackingUsersWithParallelStreaming(users);	
//
////		rewardsService.setDefaultProximityBuffer();
//
//		List<UserRewardDTO> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0).toString());
////		tourGuideService.tracker.stopTracking();
//
//		assertEquals(gpsUtilMicroService.getAttractions().size(), userRewards.size());
//	}



	// ##############################################################

		
}
