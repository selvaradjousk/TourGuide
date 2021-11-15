package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private final RewardsService rewardsService;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;

	private InternalTestHelper internalTestHelper
						= new InternalTestHelper();



	// ##############################################################


	public TourGuideService(
			GpsUtil gpsUtil,
			RewardsService rewardsService,
			Tracker tracker,
			InternalTestHelper internalTestHelper) {

		super();
		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;
		this.tracker = tracker;
		this.internalTestHelper = internalTestHelper;
	}



	// ##############################################################



	public TourGuideService(
			GpsUtil gpsUtil,
			RewardsService rewardsService) {

		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;


		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			internalTestHelper.initializeInternalUsers();
			logger.debug("Finished initializing users");
		}

		tracker = new Tracker(this);
		addShutDownHook();
	}



	// ##############################################################



	@Override
	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}



	// ##############################################################


	
	@Override
	public VisitedLocation getUserLocation(User user) {

		VisitedLocation visitedLocation
				= (user.getVisitedLocations().size() > 0) ?
						user.getLastVisitedLocation() :
							trackUserLocation(user);

		return visitedLocation;
	}



	// ##############################################################



	@Override
	public User getUser(String userName) {

		return internalTestHelper
				.internalUserMap
				.get(userName);
	}



	// ##############################################################



	@Override
	public List<User> getAllUsers() {

		return internalTestHelper
				.internalUserMap
				.values()
				.stream()
				.collect(Collectors.toList());
	}



	// ##############################################################



	@Override
	public void addUser(User user) {

		if(!internalTestHelper
				.internalUserMap
				.containsKey(user.getUserName())) {

			internalTestHelper
			.internalUserMap.put(
					user.getUserName(),
					user);
		}
	}



	// ##############################################################



	@Override
	public List<Provider> getTripDeals(User user) {

		int cumulatativeRewardPoints = user
				.getUserRewards()
				.stream()
				.mapToInt(i -> i.getRewardPoints())
				.sum();

		List<Provider> providers = tripPricer.getPrice(
				internalTestHelper
				.tripPricerApiKey,
				user.getUserId(),
				user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(),
				user.getUserPreferences().getTripDuration(),
				cumulatativeRewardPoints);

		user.setTripDeals(providers);

		return providers;
	}



	// ##############################################################



	@Override
	public VisitedLocation trackUserLocation(User user) {

		VisitedLocation visitedLocation
					= gpsUtil.getUserLocation(user.getUserId());

		user.addToVisitedLocations(visitedLocation);

		rewardsService.calculateRewards(user);

		return visitedLocation;
	}



	// ##############################################################



	@Override
	public List<Attraction> getNearByAttractions(
			VisitedLocation visitedLocation) {

		List<Attraction> nearbyAttractions = new ArrayList<>();

		for(Attraction attraction : gpsUtil.getAttractions()) {

			if(rewardsService.isWithinAttractionProximity(
					attraction,
					visitedLocation.location)) {

				nearbyAttractions.add(attraction);

			}
		}
		
		return nearbyAttractions;
	}



	// ##############################################################



	private void addShutDownHook() {

		Runtime.getRuntime().addShutdownHook(new Thread() { 

			public void run() {
		        tracker.stopTracking();

			} 
		}); 
	}



	// ##############################################################



}
