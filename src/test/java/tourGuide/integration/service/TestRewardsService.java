package tourGuide.integration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.service.GpsUtilMicroService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.tracker.Tracker;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/integration-test.properties")
public class TestRewardsService {


    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private TourGuideService tourGuideService;


    private Tracker tracker;

    @Autowired
    private GpsUtilMicroService gpsUtilMicroService;

	// ##############################################################

	
	@Test
	public void userGetRewards() throws InterruptedException {

		Locale.setDefault(Locale.US);

//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());

//		InternalTestHelper.setInternalUserNumber(0);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		AttractionDTO attraction = gpsUtilMicroService.getAttractions().get(0);

		user.addToVisitedLocations(new VisitedLocation(
				user.getUserId(),
				attraction.getLocation(),
				new Date()));

		List<User> users = new ArrayList<>();

		users.add(user);
		
		tracker.trackingUsersWithParallelStreaming(users);		

		List<UserReward> userRewards = user.getUserRewards();
//		tourGuideService.tracker.stopTracking();

		assertEquals(1, userRewards.size());
	}



	// ##############################################################

		
//	@Test
//	public void isWithinAttractionProximity() {
//
//		Locale.setDefault(Locale.US);
//
////		GpsUtil gpsUtil = new GpsUtil();
////		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		AttractionDTO attraction = gpsUtilMicroService.getAttractions().get(0);
//		assertTrue(rewardsService.isNearAttraction(attraction, attraction));
//	}



	// ##############################################################

		
//	@Ignore // Needs fixed - can throw ConcurrentModificationException
	@Test
	public void nearAllAttractions() {

		// GpsUtil gpsUtil = new GpsUtil();
		// RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//		rewardsService.setProximityBuffer(Integer.MAX_VALUE);

//		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilMicroService, null, null, rewardsService, null, null, null, null, null, null, null, null);
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");	
		List<User> users = new ArrayList<>();

		users.add(user);
		tracker.trackingUsersWithParallelStreaming(users);	

//		rewardsService.setDefaultProximityBuffer();

		List<UserRewardDTO> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0).toString());
//		tourGuideService.tracker.stopTracking();

		assertEquals(gpsUtilMicroService.getAttractions().size(), userRewards.size());
	}



	// ##############################################################

		
}
