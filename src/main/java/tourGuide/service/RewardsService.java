package tourGuide.service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.model.User;
import tourGuide.model.UserReward;

@Service
public class RewardsService implements IRewardService {



	private Logger logger = LoggerFactory
			.getLogger(RewardsService.class);

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE
													= 1.15077945;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int attractionProximityRange = 200;



	private final RewardCentral rewardsCentral;
	private final List<Attraction> attractions;
	//	private final GpsUtil gpsUtil;


	// ##############################################################


	// Concurrency JDK API interface that simplifies running tasks
	//  in asynchronous mode as threads
    private final ExecutorService executorService = Executors
    		.newFixedThreadPool(1000);
    private int proximityBuffer = defaultProximityBuffer;

    // Concurrency construct that allows one or more threads
    // to wait for a given set of operations to complete
    CountDownLatch countDownLatch = new CountDownLatch(0);



	// ##############################################################



    @Autowired
	public RewardsService(
			GpsUtil gpsUtil,
			RewardCentral rewardCentral) {

		attractions =  gpsUtil.getAttractions();;
		this.rewardsCentral = rewardCentral;
	}



	// ##############################################################


	public void setProximityBuffer(int proximityBuffer) {

		logger.info("## setProximityBuffer() called"
				+ " : {}", proximityBuffer);

		this.proximityBuffer = proximityBuffer;

	}



	// ##############################################################


	public void setDefaultProximityBuffer() {

		logger.info("## setDefaultProximityBuffer() called");

		proximityBuffer = defaultProximityBuffer;

	}



	// ##############################################################


	@Override
	public void calculateRewards(User user) {

		// Concurrency construct for thread pool is initiated
		// for this service
        executorService.submit(() -> {
            user.getVisitedLocations().forEach(userlocation -> {

            	attractions.stream()
            	
            	// check if the visited spot is near attraction spot
                        .filter(attractionn -> nearAttraction(
                        		userlocation,
                        		attractionn))

                        // scanning for each attractions around
                        .forEach(attractionn -> {

                        	// check if user has been offered reward
                        	// for this attraction spot
                        	if (user.getUserRewards()
                            		.stream()
                            		.noneMatch(r -> r
                            				.attraction
                            				.attractionName
                            				.equals(attractionn
                            						.attractionName))) {

                        		// if not yet rewards offered
                        		// reward is added to user's list
                        		// along with reward points
                            	user.addUserReward(
                            			new UserReward(
                            					userlocation,
                            					attractionn,
                            					getRewardPoints(
                            							attractionn,
                            							user)));
                            }
                        });
            });

            // synchronizer allows one Thread to wait for one or more Threads
            // before it starts processing - Concurrency construct
            countDownLatch.countDown();

        });
    }



	// ##############################################################


	public boolean isWithinAttractionProximity(
			Attraction attraction,
			Location location) {

//		logger.info("## isWithinAttractionProximity()"
//				+ " for attraction {} & location {} called ", attraction, location);

		return !(getDistance(attraction, location)
				> attractionProximityRange);

	}



	// ##############################################################


	private boolean nearAttraction(
			VisitedLocation visitedLocation,
			Attraction attraction) {

//		logger.info("## nearAttraction()"
//				+ " for attraction {} & visitedLocation"
//				+ " {} called ", attraction, visitedLocation);

		return !(getDistance(
				attraction,
				visitedLocation.location)
				> proximityBuffer);
	}



	// ##############################################################


	public int getRewardPoints(
			Attraction attraction,
			User user) {

//		logger.info("## getRewardPoints()"
//				+ " for attraction {} & user {} called ", attraction, user.getUserName());

		return rewardsCentral
				.getAttractionRewardPoints(
						attraction.attractionId,
						user.getUserId());

	}



	// ##############################################################


	public double getDistance(Location loc1, Location loc2) {

//		logger.info("## getDistance location1 {}"
//				+ " & location2 {} called: ",
//				loc1, loc2);

		double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

//        logger.info("## latitude1: {}", lat1);
//        logger.info("## latitude2: {}", lat2);
//        logger.info("## longitude1: {}", lon1);
//        logger.info("## longitude2: {}", lon2);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1)
                               * Math.cos(lat2)
                               * Math.cos(lon1 - lon2));

//        logger.info("## angle: {}", angle);

        double nauticalMiles = 60 * Math.toDegrees(angle);

//        logger.info("## nauticalMiles: {}", nauticalMiles);

        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE
        		* nauticalMiles;

//        logger.info("## statuteMiles: {}", statuteMiles);

        return statuteMiles;
	}





	// ##############################################################

    // Methods used for Testing Purpose
    @Profile("test")
    public void rewardAndWait(List<User> users)
    					throws InterruptedException {
        
    	countDownLatch = new CountDownLatch(users.size());

    	users.forEach(this::calculateRewards);

    	// Concurrency construct synchronizer waits Threads
    	// for the user list size to complete the tasks
    	countDownLatch.await();
    }



	// ##############################################################

}
