package tourGuide.integration.performance;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.User;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestPerformance {

	private static Locale locale = new Locale("en", "US");


    @Autowired
    private GpsUtil gpsUtil;
    @Autowired
    private RewardsService rewardsService;



	// ##############################################################


	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */



	// ##############################################################

	
//	@Ignore
	@Test
	public void highVolumeTrackLocation() {

		Locale.setDefault(Locale.US);
		
//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		
		// ----------------------------------------------------------------------------
		// Users should be incremented up to 100,000, > 15 minutes and test finishes within 15 minutes
		// ----------------------------------------------------------------------------
		InternalTestHelper.setInternalUserNumber(6667);
		InternalTestHelper.setInternalUserNumber(100000);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
	    StopWatch stopWatch = new StopWatch();
		
//		List<User> allUsers = new ArrayList<>();
		List<User> allUsers = tourGuideService.getAllUsers();

		
		// ----------------------------------------------------------------------------
		/*
        Step 1 => TrackLocation: adds a new VisitedLocation for each user.
        Step 2 => VisitedLocation count is saved for each user
        Step 3 => VisitedLocation count will be compared to new count after tracking.
        */
		// ----------------------------------------------------------------------------
		
		// ---------
		// Step 1
		//----------
		List<Integer> initialVisitedLocationsCount = allUsers
		    		.stream()
		    		.map(u -> u
		    				.getVisitedLocations().size())
		    		.collect(Collectors.toList());
		

		stopWatch.start();
//		for(User user : allUsers) {
//			tourGuideService.trackUserLocation(user);
//		}
		
		tourGuideService.tracker.trackAndWait(tourGuideService.getAllUsers());
		
		stopWatch.stop();
//		tourGuideService.tracker.stopTracking();

		//----------
		// Step 2
		//----------
		List<Integer> newVisitedLocationsCount = allUsers
	    		.stream()
	    		.map(u -> u
	    				.getVisitedLocations().size())
	    		.collect(Collectors.toList());

		//----------
		// Step 3
		// ----------------------------------------------------------------------------
        // Comparison of VisitedLocations count
		// ----------------------------------------------------------------------------
        for (int i = 0; i < initialVisitedLocationsCount.size(); i++) {
            assertEquals(initialVisitedLocationsCount.get(i) + 1, (int) newVisitedLocationsCount.get(i));
        }

        System.out.println("highVolumeTrackLocation check if (6667 users < 1 minute) : Actual Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(1) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}



	// ##############################################################

	
//	@Ignore
	@Test
	public void highVolumeGetRewards() throws InterruptedException {
		
		Locale.setDefault(locale);


//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());

		// ----------------------------------------------------------------------------
		// Users should be incremented up to 100,000, < 20 minutes (5000 < 1 minutes) and test finishes within 20 minutes
		// ----------------------------------------------------------------------------
		
		// InternalTestHelper.setInternalUserNumber(100);
		InternalTestHelper.setInternalUserNumber(5000);

		StopWatch stopWatch = new StopWatch();

		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		List<User> allUsers = tourGuideService.getAllUsers();
		
//	    Attraction attraction = gpsUtil.getAttractions().get(0);
//		List<User> allUsers = new ArrayList<>();

		allUsers.forEach(u -> u
				.addToVisitedLocations(
						new VisitedLocation(
								u.getUserId(),
								gpsUtil.getAttractions().get(0),
								new Date()))
				);
		
		stopWatch.start();
		
		rewardsService.rewardAndWait(allUsers);
//	    allUsers.forEach(u -> rewardsService.calculateRewards(u));

		stopWatch.stop();
//		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeGetRewards check if (5000 rewards < 1 minute) : Actual Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 

		for(User user : allUsers) {
		assertTrue(user.getUserRewards().size() > 0);
		}
		
		assertTrue(TimeUnit.MINUTES.toSeconds(1) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));

	}



	// ##############################################################

	
}
