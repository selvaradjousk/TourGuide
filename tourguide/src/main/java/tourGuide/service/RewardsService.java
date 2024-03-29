package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxy.MicroserviceGpsProxy;
import tourGuide.proxy.MicroserviceRewardsProxy;
import tourGuide.util.AttractionMapper;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.UserRewardMapper;
/**
 * The Class RewardsService.
 */
@Service
public class RewardsService implements IRewardService {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(RewardsService.class);


	/** The Constant DEFAULT_PROXIMITY_BUFFER. */
	public static final int DEFAULT_PROXIMITY_BUFFER = 10;

    /** The user service. */
    private final IUserService userService;

	/** The gps util micro service. */
    private final MicroserviceGpsProxy gpsUtilMicroService;
	
    /** The rewards micro service. */
    private MicroserviceRewardsProxy rewardsMicroService;

    /** The attraction mapper. */
    private final AttractionMapper attractionMapper;

    /** The attraction mapper. */
    private final UserRewardMapper userRewardMapper;

    /** The distance calculator. */
    private final DistanceCalculator distanceCalculator;



    // ##############################################################

	/** The executor service. */
	// Concurrency JDK API interface that simplifies running tasks
	//  in asynchronous mode as threads
    private final ExecutorService executorService = Executors
    		.newFixedThreadPool(1000);



	// ##############################################################




/**
 * Instantiates a new rewards service.
 *
 * @param gpsUtilMicroService the gps util micro service
 * @param rewardsMicroService the rewards micro service
 * @param attractionMapper the attraction mapper
 * @param distanceCalculator the distance calculator
 */
@Autowired
    public RewardsService(
    		final MicroserviceGpsProxy gpsUtilMicroService,
    		final MicroserviceRewardsProxy rewardsMicroService,
    		final IUserService userService,
            final AttractionMapper attractionMapper,
            final UserRewardMapper userRewardMapper,
            final DistanceCalculator distanceCalculator) {
        this.gpsUtilMicroService = gpsUtilMicroService;
        this.rewardsMicroService = rewardsMicroService;
        this.userService = userService;
        this.attractionMapper = attractionMapper;
        this.userRewardMapper = userRewardMapper;
        this.distanceCalculator = distanceCalculator;
    }



// ##############################################################



		/**
		 * Gets the user rewards.
		 *
		 * @param userName the user name
		 * @return the user rewards
		 */
		@Override
		public List<UserRewardDTO> getUserRewards(
				final String userName) {
		
			logger.info("## getUserRewards list for"
					+ " user {} retrieved ", userName );
		
		    User user = userService.getUser(userName);
		
		
		    List<UserRewardDTO> userRewards = new ArrayList<>();
		    user.getUserRewards().stream()
		            .forEach(reward -> {
		                userRewards.add(userRewardMapper
		                		.toUserRewardDTO(reward));
		            });
		
		    return userRewards;
			
		}
		
		
		
		// ##############################################################


	/**
	 * Calculate rewards.
	 *
	 * @param user the user
	 */
	@Override
	public void calculateRewards(final User user) {

		// Thread-safe variant of ArrayList (creates a clone of the underlying array)
		// used in a Thread based environment where read operations are very
		// frequent and update operations are rare
		
        CopyOnWriteArrayList<VisitedLocation> userLocations
        			= new CopyOnWriteArrayList<>();

        CopyOnWriteArrayList<AttractionDTO> attractions
        			= new CopyOnWriteArrayList<>();

        userLocations.addAll(user.getVisitedLocations());
        attractions.addAll(gpsUtilMicroService.getAttractions());

        userLocations.stream().forEach(visitedLocation -> {
            attractions.stream()

            // check if the visited spot is near attraction spot
            .filter(attraction -> isWithinAttractionProximity(
            		visitedLocation,
            		attraction.getLocation()))

                    // scanning for each attractions around
                    .forEach(attraction -> {


                    	// check if user has been offered reward
                    	// for this attraction spot
                    	if (checkIfUserIsOfferedRewardToThisAttractionSpot(
                    			user,
                    			attraction)) {

                    		// if not yet rewards offered
                    		// reward is added to user's list
                    		// along with reward points
                        	user.addUserReward(
                        			getRewardPoints(
                        					user,
                        					visitedLocation,
                        					attraction));
                        }
                    });
        });
    }


	// ##############################################################


	/**
	 * Check if user is offered reward to this attraction spot.
	 *
	 * @param user the user
	 * @param attraction the attraction
	 * @return true, if successful
	 */
	public boolean checkIfUserIsOfferedRewardToThisAttractionSpot(
			final User user,
			AttractionDTO attraction) {

		return user.getUserRewards().stream()
		        .noneMatch(r -> r.attraction.getAttractionName()
		        		.equals(attraction.getAttractionName()));
	}


	// ##############################################################


	/**
	 * Gets the reward points.
	 *
	 * @param user the user
	 * @param visitedLocation the visited location
	 * @param attraction the attraction
	 * @return the reward points
	 */
	public UserReward getRewardPoints(
			final User user,
			VisitedLocation visitedLocation,
			AttractionDTO attraction) {

		return new UserReward(
				visitedLocation,
				attractionMapper.toAttraction(attraction),
				
				rewardsMicroService.getRewardPoints(
						attraction.getAttractionId(),
						user.getUserId()));
	}


	// ##############################################################




    /**
	 * Calculate reward async.
	 *
	 * @param user the user
	 * @return the completable future
	 */
	public CompletableFuture<?> calculateRewardAsync(
    		final User user) {


        return CompletableFuture.runAsync(() -> {
            this.calculateRewards(user);
        }, executorService);
    }




	// ##############################################################



    /**
	 * Checks if is within attraction proximity.
	 *
	 * @param visitedLocation the visited location
	 * @param attractionLocation the attraction location
	 * @return true, if is within attraction proximity
	 */
	public boolean isWithinAttractionProximity(
    		final VisitedLocation visitedLocation,
    		final Location attractionLocation) {


        return !(
        		distanceCalculator.getDistanceInMiles(
        				attractionLocation,
        				visitedLocation.getLocation()) >
                DEFAULT_PROXIMITY_BUFFER);
    }



	// ##############################################################


}
