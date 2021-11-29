package tourGuide.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.dto.NearbyAttractionDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.model.UserReward;
import tourGuide.tracker.Tracker;
import tourGuide.util.UserPreferencesMapper;
import tripPricer.Provider;
import tripPricer.TripPricer;

/**
 * The Class TourGuideService.
 */
@Service
public class TourGuideService implements ITourGuideService {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(TourGuideService.class);

	/** The gps util. */
	private final GpsUtil gpsUtil;

	/** The rewards service. */
	private final IRewardService rewardsService;

	/** The trip pricer. */
	private final TripPricer tripPricer = new TripPricer();

	/** The tracker. */
	public final Tracker tracker;

	/** The test mode. */
	boolean testMode = true;

	
	/** The internal test helper. */
	private InternalTestHelper internalTestHelper
						= new InternalTestHelper();



	// ##############################################################


//	public TourGuideService(
//			GpsUtil gpsUtil,
//			RewardsService rewardsService,
//			Tracker tracker,
//			InternalTestHelper internalTestHelper) {
//
//		super();
//		this.gpsUtil = gpsUtil;
//		this.rewardsService = rewardsService;
//		this.tracker = tracker;
//		this.internalTestHelper = internalTestHelper;
//	}



	// ##############################################################



	/**
	 * Instantiates a new tour guide service.
	 *
	 * @param gpsUtil the gps util
	 * @param rewardsService the rewards service
	 */
	public TourGuideService(
			GpsUtil gpsUtil,
			IRewardService rewardsService) {

		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;


		if(testMode) {

			logger.info("TestMode enabled");

			logger.debug("Initializing users");

			internalTestHelper.initializeInternalUsers();

			logger.debug("Finished initializing users");
		}

		this.tracker = new Tracker(this, gpsUtil, rewardsService);

		logger.info("## Tracker instance initiated");

		addShutDownHook();

		logger.info("## addShutDownHook() called");

	}



	// ##############################################################



	/**
	 * Gets the user rewards.
	 *
	 * @param user the user
	 * @return the user rewards
	 */
	@Override
	public List<UserReward> getUserRewards(User user) {

		logger.info("## getUserRewards list for"
				+ " user {} retrieved ", user );


		return user.getUserRewards();
	}



	// ##############################################################


	
	/**
	 * Gets the user location.
	 *
	 * @param user the user
	 * @return the user location
	 */
	@Override
	public VisitedLocation getUserLocation(User user) {

		logger.info("## getUserLocation"
				+ " for user {} invoked ", user.getUserName() );

		VisitedLocation visitedLocation;

		// get user location if not null
		if(user.getVisitedLocations().size() > 0) {
			visitedLocation = user.getLastVisitedLocation();
		} else {
			// gpsUtil used to fetch location if found null
			visitedLocation = gpsUtil.getUserLocation(user.getUserId());
			user.addToVisitedLocations(visitedLocation);
		}

		logger.info("## Visited Location for"
				+ " user {} is: {} ", user, visitedLocation);

		return visitedLocation;
	}



	// ##############################################################



	/**
	 * Gets the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	@Override
	public User getUser(String userName) {

		logger.info("## getUser() for user {} invoked ", userName );

		return internalTestHelper
				.internalUserMap
				.get(userName);
	}



	// ##############################################################



	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@Override
	public List<User> getAllUsers() {

		logger.info("## getAllUser() method invoked");

		return new ArrayList<>(internalTestHelper.internalUserMap.values());
		
//		return internalTestHelper
//				.internalUserMap
//				.values()
//				.stream()
//				.collect(Collectors.toList());
	}



	// ##############################################################


	/**
	 * Gets the all users last location.
	 *
	 * @return the all users last location
	 */
	public HashMap<String, Location> getAllUsersLastLocation() {

		HashMap<String, Location> usersLastLocation = new HashMap<>();

		getAllUsers()
				.forEach(u -> usersLastLocation
						.put(u
								.getLastVisitedLocation().userId.toString(),
								u.getLastVisitedLocation().location));

		return usersLastLocation;

	}

	// ##############################################################


	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	@Override
	public void addUser(User user) {

		logger.info("## addUser() for user {} invoked ", user );

		if(!internalTestHelper
				.internalUserMap
				.containsKey(user.getUserName())) {

			logger.info("## addUser() for"
					+ " user {} gets added"
					+ " to map ", user.getUserName() );

			internalTestHelper
			.internalUserMap.put(
					user.getUserName(),
					user);
		}
	}



	// ##############################################################



	/**
	 * Gets the trip deals.
	 *
	 * @param user the user
	 * @return the trip deals
	 */
	@Override
	public List<Provider> getTripDeals(User user) {

		logger.info("## getTripDeals() method for"
				+ " user {} invoked", user);

		int cumulativeRewardPoints = user
				.getUserRewards()
				.stream()
//				.mapToInt(i -> i.getRewardPoints())
				.mapToInt(UserReward::getRewardPoints)
				.sum();

		logger.info("## cumulative points for"
				+ " user {} : {}", user.getUserName(), cumulativeRewardPoints);


		List<Provider> providers = tripPricer.getPrice(
				internalTestHelper
				.tripPricerApiKey,
				user.getUserId(),
				user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(),
				user.getUserPreferences().getTripDuration(),
				cumulativeRewardPoints);

		logger.info("## Providers for"
				+ " user {} : {}", user.getUserName(), providers);


		user.setTripDeals(providers);

		logger.info("## TripDeals for"
				+ " user {} : {}", user.getUserName(), user.getTripDeals());

		return providers;
	}



	// ##############################################################

    /**
	 * Update user preferences.
	 *
	 * @param userName the user name
	 * @param userPreferencesDTO the user preferences DTO
	 * @return true, if successful
	 */
	public boolean updateUserPreferences(
    		String userName,
    		UserPreferencesDTO userPreferencesDTO) {

    	if (!internalTestHelper.internalUserMap.containsKey(userName)) {
            return false;
        }

//        User user = getUser(userName);
    	User user = internalTestHelper
    			.internalUserMap.get(userName);

    	UserPreferences userPreferences = new UserPreferencesMapper()
    			.toUserPreferences(userPreferencesDTO);

        user.setUserPreferences(userPreferences);

        internalTestHelper.internalUserMap.put(userName, user);

        return true;
    }



	// ##############################################################


//
//	@Override
//	public VisitedLocation trackUserLocation(User user) {
//
////		logger.info("## trackUserLocation() for"
////				+ " user {} invoked", user.getUserName());
//
//		VisitedLocation visitedLocation
//					= gpsUtil.getUserLocation(user.getUserId());
//
////		logger.info("## visitedLocation for"
////				+ " user {} : {}", user.getUserName(), visitedLocation);
//
//		user.addToVisitedLocations(visitedLocation);
//
//		rewardsService.calculateRewards(user);
//
//		return visitedLocation;
//	}
//





	// ##############################################################



	/**
	 * Gets the user attraction recommendation.
	 *
	 * @param username the username
	 * @return the user attraction recommendation
	 */
	public UserAttractionRecommendationDTO getUserAttractionRecommendation(String username) {

		VisitedLocation userLastLocation = getUser(username).getLastVisitedLocation();

		List<Attraction> nearbyAttractions = getNearByAttractions(userLastLocation);

		Map<String, NearbyAttractionDTO> nearbyAttractionHashMap = new HashMap<>();

		nearbyAttractions.forEach(att ->
				nearbyAttractionHashMap.put(
						att.attractionName,
						new NearbyAttractionDTO(
								att.latitude,
								att.longitude,
								rewardsService.getDistance(att, userLastLocation.location),
								rewardsService.getRewardPoints(att, getUser(username))))
		);

		return new UserAttractionRecommendationDTO(
				userLastLocation.location,
				nearbyAttractionHashMap);

	}


	// ##############################################################




	/**
	 * Gets the near by attractions.
	 *
	 * @param visitedLocation the visited location
	 * @return the near by attractions
	 */
	@Override
	public List<Attraction> getNearByAttractions(
			VisitedLocation visitedLocation) {

		logger.info("## getNearByAttractions() for"
				+ " visitedLocation - {} called", visitedLocation);

		List<Attraction> nearbyAttractions = gpsUtil.getAttractions();

        nearbyAttractions = nearbyAttractions.stream()
                .sorted(Comparator.comparing(
                        attraction -> rewardsService.getDistance(
                        		visitedLocation.location,
                        		attraction)))
                .limit(5)
                .collect(Collectors.toList());

			logger.info("## NearByAttractions for"
					+ " visitedLocation - {}"
					+ " : {}", visitedLocation, nearbyAttractions);

		
		return nearbyAttractions;
	}



	// ##############################################################



	/**
	 * Adds the shut down hook.
	 */
	private void addShutDownHook() {

		logger.info("## addShutDownHook invoked ");

		Runtime.getRuntime().addShutdownHook(new Thread() { 

			public void run() {

				 if (!testMode) {
					 tracker.stopTracking();
				 }

				logger.info("## tracker.stopTracking invoked ");

			}

		}); 
	}



	// ##############################################################



}
