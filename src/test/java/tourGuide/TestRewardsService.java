package tourGuide;

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
import org.springframework.test.context.junit4.SpringRunner;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRewardsService {


	@Autowired
	private GpsUtil gpsUtil;

	@Autowired
	private RewardsService rewardsService;



	// ##############################################################

	
	@Test
	public void userGetRewards() {

		Locale.setDefault(Locale.US);

//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());

		InternalTestHelper.setInternalUserNumber(0);
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsUtil.getAttractions().get(0);
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
//		tourGuideService.trackUserLocation(user);

		List<User> users = new ArrayList<>();

		users.add(user);
		
		try {
			rewardsService.rewardAndWait(users);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		

		List<UserReward> userRewards = user.getUserRewards();
//		tourGuideService.tracker.stopTracking();

		assertEquals(1, userRewards.size());
	}



	// ##############################################################

		
	@Test
	public void isWithinAttractionProximity() {

		Locale.setDefault(Locale.US);

//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		Attraction attraction = gpsUtil.getAttractions().get(0);
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
	}



	// ##############################################################

		
//	@Ignore // Needs fixed - can throw ConcurrentModificationException
	@Test
	public void nearAllAttractions() {

		// GpsUtil gpsUtil = new GpsUtil();
		// RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		rewardsService.setProximityBuffer(Integer.MAX_VALUE);

		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		

		try {
			rewardsService.rewardAndWait(tourGuideService.getAllUsers());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rewardsService.setDefaultProximityBuffer();

		List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
//		tourGuideService.tracker.stopTracking();

		assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
	}



	// ##############################################################

		
}
