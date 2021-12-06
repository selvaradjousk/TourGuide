package tripDeals.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tripDeals.dto.ProviderDTO;
import tripDeals.exception.UserNotFoundException;
import tripDeals.service.ITripDealsService;


/**
 * The Class TripDealsController.
 */
@RestController
@RequestMapping("/tripDeals")
public class TripDealsController {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(TripDealsController.class);


	/** The trip deals service. */
	private ITripDealsService tripDealsService;

	//###############################################################


	/**
	 * Instantiates a new trip deals controller.
	 *
	 * @param tripDealsService the trip deals service
	 */
	public TripDealsController(
			final ITripDealsService tripDealsService) {
		super();
		this.tripDealsService = tripDealsService;
	}


	//###############################################################

    /**
	 * Gets the providers.
	 *
	 * @param apiKey the api key
	 * @param userId the user id
	 * @param adults the adults
	 * @param children the children
	 * @param nightsStay the nights stay
	 * @param rewardPoints the reward points
	 * @return the providers
	 */
	@GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardPoints}")
    public List<ProviderDTO> getProviders(
    		@PathVariable final String apiKey,
    		@PathVariable final UUID userId,
            @PathVariable final int adults,
            @PathVariable final int children,
            @PathVariable final int nightsStay,
            @PathVariable final int rewardPoints) {

    	logger.debug("getProviders for user id: {}", userId.toString());

        List<ProviderDTO> providers = tripDealsService
        		.getProviders(
        				apiKey,
        				userId,
        				adults,
        				children,
        				nightsStay,
        				rewardPoints);

        if (providers.isEmpty()) {
            throw new UserNotFoundException("unable to get provider list");
        }

        logger.debug("provider list returned successfully");

        return providers;
    }


	//###############################################################


}
