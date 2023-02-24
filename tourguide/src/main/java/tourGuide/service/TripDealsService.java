package tourGuide.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.dto.ProviderDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.proxy.MicroServiceTripDealsProxy;
import tourGuide.tracker.Tracker;
import tourGuide.util.ProviderMapper;

@Service
public class TripDealsService implements ITripDealsService {


	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(TripDealsService.class);

    /** The user service. */
    private final IUserService userService;

    /** The trip deals micro service. */
    private final MicroServiceTripDealsProxy tripDealsMicroService;
    
    /** The provider mapper. */
    private final ProviderMapper providerMapper;	

    /** The internal test helper. */
	private InternalTestHelper internalTestHelper
						= new InternalTestHelper();

	/** The tracker. */
	public Tracker tracker;



	// ##############################################################



	@Autowired
	public TripDealsService(
			Tracker tracker,
			IUserService userService,
			MicroServiceTripDealsProxy tripDealsMicroService,
			ProviderMapper providerMapper,
			InternalTestHelper internalTestHelper) {

		this.tracker = tracker;
		this.userService = userService;
		this.tripDealsMicroService = tripDealsMicroService;
		this.providerMapper = providerMapper;
		this.internalTestHelper = internalTestHelper;
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

		User user = userService.getUser(userName);

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




}
