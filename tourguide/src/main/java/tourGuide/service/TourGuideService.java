package tourGuide.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.NearByAttractionDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.UserNotFoundException;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.proxy.MicroServiceTripDealsProxy;
import tourGuide.tracker.Tracker;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.LocationMapper;
import tourGuide.util.ProviderMapper;
import tourGuide.util.UserPreferencesMapper;
import tourGuide.util.UserRewardMapper;
import tourGuide.util.VisitedLocationMapper;


/**
 * The Class TourGuideService.
 */
@Service
public class TourGuideService implements ITourGuideService {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(TourGuideService.class);

    /** The executor service. */
    private final ExecutorService executorService = Executors
    		.newFixedThreadPool(1000);

    /** The gps util micro service. */
    private final IGpsUtilMicroService gpsUtilMicroService;

    /** The rewards micro service. */
    private RewardsMicroService rewardsMicroService;

    /** The trip deals micro service. */
    private final MicroServiceTripDealsProxy tripDealsMicroService;

    /** The rewards service. */
    private final IRewardService rewardsService;

    /** The user preferences mapper. */
    private final UserPreferencesMapper userPreferencesMapper;

    /** The user reward mapper. */
    private final UserRewardMapper userRewardMapper;

    /** The location mapper. */
    private final LocationMapper locationMapper;

    /** The visited location mapper. */
    private final VisitedLocationMapper visitedLocationMapper;
    
    /** The provider mapper. */
    private final ProviderMapper providerMapper;

    /** The distance calculator. */
    private final DistanceCalculator distanceCalculator;

	/** The tracker. */
	public Tracker tracker;

	/** The test mode. */
    @Value("${test.mode.enabled}")
    private boolean isTestMode;

    /** The is performance test. */
    @Value("${performance.test.enabled}")
    private boolean isPerformanceTest;
	
	/** The internal test helper. */
	private InternalTestHelper internalTestHelper
						= new InternalTestHelper();


	// ##############################################################





    /**
	 * Instantiates a new tour guide service.
	 *
	 * @param gpsUtilMicroService the gps util micro service
	 * @param rewardsMicroService the rewards micro service
	 * @param tripDealsMicroService the trip deals micro service
	 * @param rewardsService the rewards service
	 * @param internalTestHelper the internal test helper
	 * @param userPreferencesMapper the user preferences mapper
	 * @param userRewardMapper the user reward mapper
	 * @param locationMapper the location mapper
	 * @param visitedLocationMapper the visited location mapper
	 * @param providerMapper the provider mapper
	 * @param distanceCalculator the distance calculator
	 */
	@Autowired
    public TourGuideService(
    		final IGpsUtilMicroService gpsUtilMicroService,
    		final RewardsMicroService rewardsMicroService,
    		final MicroServiceTripDealsProxy tripDealsMicroService,
    		final IRewardService rewardsService,
    		final InternalTestHelper internalTestHelper,
    		final UserPreferencesMapper userPreferencesMapper,
    		final UserRewardMapper userRewardMapper,
    		final LocationMapper locationMapper,
    		final VisitedLocationMapper visitedLocationMapper,
    		final ProviderMapper providerMapper,
    		final DistanceCalculator distanceCalculator) {

        this.gpsUtilMicroService = gpsUtilMicroService;
        this.rewardsMicroService = rewardsMicroService;
        this.tripDealsMicroService = tripDealsMicroService;
        this.rewardsService = rewardsService;
        this.internalTestHelper = internalTestHelper;
        this.userPreferencesMapper = userPreferencesMapper;
        this.userRewardMapper = userRewardMapper;
        this.locationMapper = locationMapper;
        this.visitedLocationMapper = visitedLocationMapper;
        this.providerMapper = providerMapper;
        this.distanceCalculator = distanceCalculator;
    }

		


	// ##############################################################



    /**
	 * Initialization.
	 */
	@PostConstruct
    public void initialization() {

        if (isTestMode) {

			logger.info("TestMode enabled");

			logger.debug("Initializing users");

			internalTestHelper.initializeInternalUsers();

			logger.debug("Finished initializing users");
        }

        if (!isPerformanceTest) {
        	
            this.tracker = new Tracker(this);
    
    		logger.info("## Tracker instance initiated");
    
            tracker.startTracking();
        }
    }



	// ##############################################################
	// ######################## OK ##################################



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

        User user = getUser(userName);


        List<UserRewardDTO> userRewards = new ArrayList<>();
        user.getUserRewards().stream()
                .forEach(reward -> {
                    userRewards.add(userRewardMapper
                    		.toUserRewardDTO(reward));
                });

        return userRewards;
		
//		return user.getUserRewards();
	}



	// ##############################################################


    /**
	 * Gets the user location.
	 *
	 * @param userName the user name
	 * @return the user location
	 */
	public LocationDTO getUserLocation(final String userName) {

//    	logger.info("## getUserLocation"
//		+ " for user {} invoked ", userName );

        User user = getUser(userName);

        if (user.getVisitedLocations().size() > 0) {
            return locationMapper
            		.toLocationDTO(
            				user.getLastVisitedLocation().getLocation());
        }

        return locationMapper.toLocationDTO(
        		gpsUtilMicroService.getUserLocation(
        				user.getUserId()).getLocation());
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

//		logger.info("## getUser() for user {} invoked ", userName );

		User user = internalTestHelper.getInternalUserMap().get(userName);

        if (user == null) {
            throw new UserNotFoundException("Username not found");
        }

		return user;
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

		List<User> users = internalTestHelper
				.getInternalUserMap()
				.values()
				.stream()
				.collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new UserNotFoundException("User List unavailable");
        }

        return users;		
		
	}



	// ##############################################################


	/**
	 * Gets the all users last location.
	 *
	 * @return the all users last location
	 */
	public Map<String, LocationDTO> getAllUserRecentLocation() {

        return getAllUsers()
        		.stream()
        		.collect(
        				Collectors.toMap(
        						u -> u.getUserId().toString(),
        						u -> getUserLocation(u.getUserName())));
    }

	// ##############################################################

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	@Override
	public void addUser(final User user) {

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
		} else {
			throw new DataAlreadyRegisteredException(""
					+ "Username exists already");
		}
	}



	// ##############################################################



	/**
	 * Gets the trip deals.
	 *
	 * @param userName the user name
	 * @return the trip deals
	 */
	@Override
	public List<ProviderDTO> getUserTripDeals(
			final String userName) {

		logger.info("## getTripDeals() method for"
				+ " user {} invoked", userName);

		User user = getUser(userName);

		int cumulativeRewardPoints = user
				.getUserRewards()
				.stream()
				.mapToInt(r -> r
						.getRewardPoints())
				.sum();

		logger.info("## cumulative points for"
				+ " user {} : {}", user.getUserName(), cumulativeRewardPoints);


		List<ProviderDTO> providers = getProvidersDTOListForUser(
				user,
				cumulativeRewardPoints);


        List<Provider> providerList = mapProvidersDTOListToProvidersListDO(
        		providers);
        
        
		user.setTripDeals(providerList);

		logger.info("## TripDeals for"
				+ " user {} : {}", user.getUserName(), user.getTripDeals());

		return providers;
	}




	// ##############################################################




	/**
	 * Gets the providers DTO list for user.
	 *
	 * @param user the user
	 * @param cumulativeRewardPoints the cumulative reward points
	 * @return the providers DTO list for user
	 */
	private List<ProviderDTO> getProvidersDTOListForUser(
			User user,
			int cumulativeRewardPoints) {

		List<ProviderDTO> providers = tripDealsMicroService
				.getProviders(
						internalTestHelper.getTripPricerApiKey(),
						user.getUserId(),
						user.getUserPreferences().getNumberOfAdults(),
						user.getUserPreferences().getNumberOfChildren(),
						user.getUserPreferences().getTripDuration(),
						cumulativeRewardPoints);

		logger.info("## Providers for"
				+ " user {} : {}", user.getUserName(), providers);

		return providers;
	}



	// ##############################################################



	/**
	 * Map providers DTO list to providers list DO.
	 *
	 * @param providers the providers
	 * @return the list
	 */
	private List<Provider> mapProvidersDTOListToProvidersListDO(
			List<ProviderDTO> providers) {

		List<Provider> providerList = new ArrayList<>();

		providers.forEach(provider -> {
            providerList.add(providerMapper.toProvider(provider));

		});
		return providerList;
	}



	// ##############################################################



 
	/**
	 * Update user preferences.
	 *
	 * @param userName the user name
	 * @param userPreferencesDTO the user preferences DTO
	 * @return the user preferences DTO
	 */
	public UserPreferencesDTO updateUserPreferences(
    		final String userName,
    		final UserPreferencesDTO userPreferencesDTO) {



        User user = getUser(userName);


        UserPreferences userPreferences = user
        		.getUserPreferences();
        userPreferences.setAttractionProximity(userPreferencesDTO
        		.getAttractionProximity());
        userPreferences.setHighPricePoint(Money.of(userPreferencesDTO
        		.getHighPricePoint(), userPreferences
        		.getCurrency()));
        userPreferences.setLowerPricePoint(Money.of(userPreferencesDTO
        		.getLowerPricePoint(), userPreferences
        		.getCurrency()));
        userPreferences.setTripDuration(userPreferencesDTO
        		.getTripDuration());
        userPreferences.setTicketQuantity(userPreferencesDTO
        		.getTicketQuantity());
        userPreferences.setNumberOfAdults(userPreferencesDTO
        		.getNumberOfAdults());
        userPreferences.setNumberOfChildren(userPreferencesDTO
        		.getNumberOfChildren());

        
        UserPreferencesDTO userPreferencesUpdated = userPreferencesMapper
        		.toUserPreferencesDTO(user.getUserPreferences());

        return userPreferencesUpdated;

    }



	// ##############################################################




	/**
	 * Track user location.
	 *
	 * @param user the user
	 * @return the completable future
	 */
	public CompletableFuture<?> trackUserLocation(final User user) {
    	
//	logger.info("## trackUserLocation() for"
//			+ " user {} invoked", user.getUserName());


        return CompletableFuture.supplyAsync(() -> {

        	VisitedLocationDTO visitedLocation = gpsUtilMicroService
            		.getUserLocation(user.getUserId());

        	user.addToVisitedLocations(visitedLocationMapper
        			.toVisitedLocation(visitedLocation));

        	CompletableFuture.runAsync(() -> {
                rewardsService.calculateRewards(user);
            });

        	return visitedLocation;

        }, executorService);
    }




	// ##############################################################




	/**
	 * Gets the user attraction recommendation.
	 *
	 * @param userName the user name
	 * @return the user attraction recommendation
	 */
	public UserAttractionRecommendationDTO getUserAttractionRecommendation(
			final String userName) {

		
        User user = getUser(userName);
        Location userLocation = locationMapper
        		.toLocation(getUserLocation(userName));

    	CompletableFuture.runAsync(() -> {
            rewardsService.calculateRewards(user);
        });
        
        List<AttractionDTO> attractions = gpsUtilMicroService
        		.getAttractions();

        Map<AttractionDTO, Double> attractionsMap = getAttractiosMap(
        		userLocation,
        		attractions);

        Map<AttractionDTO, Double> closestAttractionsMap = getClosestAttractionsMap(
        		attractionsMap);


        List<NearByAttractionDTO> nearByAttractions = getNearByAttractions(
        		user,
        		userLocation,
        		closestAttractionsMap);

        return new UserAttractionRecommendationDTO(
        		userLocation,
        		nearByAttractions);
    }


	
	// ##############################################################



	/**
	 * Gets the attractios map.
	 *
	 * @param userLocation the user location
	 * @param attractions the attractions
	 * @return the attractios map
	 */
	private Map<AttractionDTO, Double> getAttractiosMap(
			Location userLocation,
			List<AttractionDTO> attractions) {

		
		Map<AttractionDTO, Double> attractionsMap = new HashMap<>();

		// ********************************************************

		/*Sequential Streams VS Parallel stream (stream() Vs parallelStream())
		 Sequential streams outperformed parallel streams
		  when the number of elements in the collection was
		   less than 100,000. Parallel streams performed
		    significantly better than sequential streams
		     when the number of elements was more than 100,000.*/
		// ********************************************************
//        attractions.stream()
			attractions
				.stream()
                .forEach(a -> {
                    attractionsMap
                    .put(a, distanceCalculator
                    		.getDistanceInMiles(a
                    				.getLocation(), userLocation));
                });
		return attractionsMap;
	}
	



	// ##############################################################



	/**
	 * Gets the closest attractions map.
	 *
	 * @param attractionsMap the attractions map
	 * @return the closest attractions map
	 */
	private Map<AttractionDTO, Double> getClosestAttractionsMap(
			Map<AttractionDTO, Double> attractionsMap) {

		Map<AttractionDTO, Double> closestAttractionsMap = attractionsMap
        		.entrySet()
//        		.stream()
        		.stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .collect(
                		Collectors
                		.toMap(
                				Map.Entry::getKey,
                				Map.Entry::getValue,
                				(e1, e2) -> e1,
                				LinkedHashMap::new));
		return closestAttractionsMap;
	}




	// ##############################################################



	/**
	 * Gets the near by attractions.
	 *
	 * @param user the user
	 * @param userLocation the user location
	 * @param closestAttractionsMap the closest attractions map
	 * @return the near by attractions
	 */
	private List<NearByAttractionDTO> getNearByAttractions(
			User user, Location userLocation,
			Map<AttractionDTO, Double> closestAttractionsMap) {

		List<NearByAttractionDTO> nearByAttractions = new ArrayList<>();

		final AtomicInteger indexHolder = new AtomicInteger();
		final AtomicInteger totalPoints = new AtomicInteger();
        cumulativeRewardPointsForUserBasedOnNearbyAtrractions(
        		user,
        		closestAttractionsMap,
        		nearByAttractions,
				indexHolder,
				totalPoints);
        
		return nearByAttractions;
	}





	// ##############################################################



	/**
	 * Gets the attractions reward points.
	 *
	 * @param user the user
	 * @param a the a
	 * @return the attractions reward points
	 */
	private int getAttractionsRewardPoints(
			User user,
			Entry<AttractionDTO,
			Double> a) {
		return rewardsMicroService
				.getAttractionRewardPoints(
					a.getKey().getAttractionId(),
					user.getUserId());
	}


	


	// ##############################################################


		/**
	 * Gets the total reward points for user.
	 *
	 * @param userName the user name
	 * @return the total reward points for user
	 */
	public int getTotalRewardPointsForUser(
				final String userName) {


	        User user = getUser(userName);
	        Location userLocation = locationMapper
	        		.toLocation(getUserLocation(userName));

	    	CompletableFuture.runAsync(() -> {
	            rewardsService.calculateRewards(user);
	        });
	        
	        List<AttractionDTO> attractions = gpsUtilMicroService
	        		.getAttractions();

	        Map<AttractionDTO, Double> attractionsMap = getAttractiosMap(
	        		userLocation,
	        		attractions);

	        Map<AttractionDTO, Double> closestAttractionsMap = getClosestAttractionsMap(
	        		attractionsMap);

			List<NearByAttractionDTO> nearByAttractions = new ArrayList<>();

			final AtomicInteger indexHolder = new AtomicInteger();
			final AtomicInteger totalPoints = new AtomicInteger();

			cumulativeRewardPointsForUserBasedOnNearbyAtrractions(
	        		user,
	        		closestAttractionsMap,
	        		nearByAttractions,
					indexHolder,
					totalPoints);
	        
		return totalPoints.intValue();
	}




		// ##############################################################




		/**
		 * Cumulative N reward points for user based on nearby atrractions.
		 *
		 * @param user the user
		 * @param closestAttractionsMap the closest attractions map
		 * @param nearByAttractions the near by attractions
		 * @param indexHolder the index holder
		 * @param totalPoints the total points
		 */
		private void cumulativeRewardPointsForUserBasedOnNearbyAtrractions(
				User user,
				Map<AttractionDTO, Double> closestAttractionsMap,
				List<NearByAttractionDTO> nearByAttractions,
				final AtomicInteger indexHolder,
				final AtomicInteger totalPoints) {

			closestAttractionsMap.entrySet()
	        	.stream()
	                .forEach(a -> {
	                	final int index = indexHolder.getAndIncrement();
	                	
	                    nearByAttractions
	                    .add(new NearByAttractionDTO(
	                    		a.getKey().getAttractionName(),
	                            a.getKey().getLocation(),
	                            a.getValue(),
	                            getAttractionsRewardPoints(user, a)));
	                    logger.info("###########" + nearByAttractions
	                    		.get(index).getRewardPoints());
	                    
	                    totalPoints.addAndGet(nearByAttractions
	                    		.get(index).getRewardPoints());
	                    
	                    logger.info("###########" + totalPoints);
	                });
		}




	// ##############################################################


}
