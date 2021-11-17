package tourGuide.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.tracker.Tracker;
import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TourGuideService implements ITourGuideService {



	private Logger logger = LoggerFactory
			.getLogger(TourGuideService.class);

	private final GpsUtil gpsUtil;
	private final IRewardService rewardsService;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;

	
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



	@Override
	public List<UserReward> getUserRewards(User user) {

		logger.info("## getUserRewards list for"
				+ " user {} retrieved ", user );

		return user.getUserRewards();
	}



	// ##############################################################


	
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



	@Override
	public User getUser(String userName) {

		logger.info("## getUser() for user {} invoked ", userName );

		return internalTestHelper
				.internalUserMap
				.get(userName);
	}



	// ##############################################################



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



	@Override
	public List<Attraction> getNearByAttractions(
			VisitedLocation visitedLocation) {

		logger.info("## getNearByAttractions() for"
				+ " visitedLocation - {} called", visitedLocation);

		List<Attraction> nearbyAttractions = new ArrayList<>();

		for(Attraction attraction : gpsUtil.getAttractions()) {

			if(rewardsService.isWithinAttractionProximity(
					attraction,
					visitedLocation.location)) {

				nearbyAttractions.add(attraction);

			}

			logger.info("## NearByAttractions for"
					+ " visitedLocation - {}"
					+ " : {}", visitedLocation, nearbyAttractions);

		}
		
		return nearbyAttractions;
	}



	// ##############################################################



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
