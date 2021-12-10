package tourGuide.integration.performance;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.AttractionDTO;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.proxy.MicroserviceGpsProxy;
import tourGuide.service.GpsLocationService;
import tourGuide.service.RewardsService;
import tourGuide.service.UserService;

@ExtendWith(SpringExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/performance-test.properties")
public class TestPerformance {

//	private static Locale locale = new Locale("en", "US");


    @Autowired
    private MicroserviceGpsProxy gpsUtilMicroService;

    @Autowired
    private RewardsService rewardsService;
    

    @Autowired
    private UserService userService;

    @Autowired
    private GpsLocationService tourGuideService;



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
    @DisplayName("HighVolumeTrackLocation"
    		+ " - GIVEN a high volume users (100 000 users),"
    		+ " WHEN trackAllUserLocation,"
    		+ " THEN elapsed time should be <= to expected time (15 minutes")
    @Test
	public void highVolumeTrackLocation() {
		
		
		// ----------------------------------------------------------------------------
		// Users should be incremented up to 100,000, > 15 minutes and test finishes within 15 minutes
		// ----------------------------------------------------------------------------
		
		List<User> allUsers = userService.getAllUsers();
		allUsers.forEach(u -> u.clearVisitedLocations());
		
		// ----------------------------------------------------------------------------
		/*
        Step 1 => TrackLocation: adds a new VisitedLocation for each user.
        Step 2 => VisitedLocation count is saved for each user
        Step 3 => VisitedLocation count will be compared to new count after tracking.
        */
		// ----------------------------------------------------------------------------
		
		// ---------
		// Step 1 - TrackLocation: adds a new VisitedLocation for each user.
		//----------
		List<Integer> initialVisitedLocationsCount = allUsers
		    		.stream()
		    		.map(u -> u
		    				.getVisitedLocations().size())
		    		.collect(Collectors.toList());
		
        StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
        CompletableFuture<?>[] futures = allUsers.parallelStream()
                .map(tourGuideService::trackUserLocation)
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
		
		
		stopWatch.stop();

		//----------
		// Step 2 - VisitedLocation count is saved for each user
		//----------
		List<Integer> newVisitedLocationsCount = allUsers
	    		.parallelStream()
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

//        System.out.println("highVolumeTrackLocation check if (6667 users < 1 minute) : Actual Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
//		assertTrue(TimeUnit.MINUTES.toSeconds(1) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}



	// ##############################################################

	
    @DisplayName("HighVolumeGetRewards"
    		+ " - GIVEN a high volume users, (100 000 users)"
    		+ " WHEN calculateRewardAsync,"
    		+ " THEN elapsed time should be <= to expected time (20 minutes)")
	@Test
	public void highVolumeGetRewards() throws InterruptedException {

		// ----------------------------------------------------------------------------
		// Users should be incremented up to 100,000, < 20 minutes (5000 < 1 minutes) and test finishes within 20 minutes
		// ----------------------------------------------------------------------------
		

		StopWatch stopWatch = new StopWatch();


        AttractionDTO attraction = gpsUtilMicroService.getAttractions().get(0);
		List<User> allUsers = userService.getAllUsers();
		
		
        allUsers.forEach(u -> {
            u.clearVisitedLocations();
            u.getUserRewards().clear();
            u.addToVisitedLocations(
            		new VisitedLocation(
            				u.getUserId(),
            				attraction.getLocation(),
            				new Date()));
        });
		
		stopWatch.start();
		
//		rewardsService.rewardAndWait(allUsers);
//	    allUsers.forEach(u -> rewardsService.calculateRewards(u));

        CompletableFuture<?>[] futures = allUsers.stream()
                .map(rewardsService::calculateRewardAsync)
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();

		stopWatch.stop();


//		System.out.println("highVolumeGetRewards check if (5000 rewards < 1 minute) : Actual Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 

		for(User user : allUsers) {
		assertTrue(user.getUserRewards().size() > 0);
		}
		
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));

	}



	// ##############################################################

	
}
