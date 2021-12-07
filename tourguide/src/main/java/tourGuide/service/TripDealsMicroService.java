//package tourGuide.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import tourGuide.dto.ProviderDTO;
//import tourGuide.util.ProviderMapper;
//import tripPricer.TripPricer;
//
///**
// * The Class TripDealsMicroService.
// */
//@Service
//public class TripDealsMicroService implements ITripDealsMicroService {
//
//	/** The logger. */
//	private Logger logger = LoggerFactory
//			.getLogger(TripDealsMicroService.class);
//
//    /** The trip pricer. */
//    private final TripPricer tripPricer;
//
//    /** The provider mapper. */
//    private final ProviderMapper providerMapper;
//
//	// ##############################################################
//
//    /**
//	 * Instantiates a new trip deals micro service.
//	 *
//	 * @param tripPricer the trip pricer
//	 * @param providerMapper the provider mapper
//	 */
//	@Autowired
//    public TripDealsMicroService(
//    		final TripPricer tripPricer,
//
//    		final ProviderMapper providerMapper) {
//        this.tripPricer = tripPricer;
//        this.providerMapper = providerMapper;
//    }
//
//	// ##############################################################
//
//    /**
//	 * Gets the providers.
//	 *
//	 * @param apiKey the api key
//	 * @param userId the user id
//	 * @param adults the adults
//	 * @param children the children
//	 * @param nightsStay the nights stay
//	 * @param rewardPoints the reward points
//	 * @return the providers
//	 */
//	public List<ProviderDTO> getProviders(
//    		final String apiKey,
//    		final UUID userId,
//    		int adults,
//    		int children,
//            int nightsStay,
//            int rewardPoints) {
//
//		logger.info("## getProviders() called");
//
//		List<ProviderDTO> providerList = new ArrayList<>();
//		List<tripPricer.Provider> providers = tripPricer
//												.getPrice(
//														apiKey,
//														userId,
//														adults,
//														children,
//														nightsStay,
//														rewardPoints);
//		
//		providers.forEach(provider -> {
//
//			providerList.add(providerMapper
//					.toProviderDTO(provider));
//
//		});
//
//		logger.info("## providerList: {}"
//				+ " returned", providerList);
//		
//		return providerList;
//	}
//
//	// ##############################################################
//
//}
