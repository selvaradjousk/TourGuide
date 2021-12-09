package tourGuide.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.dto.ProviderDTO;
import tourGuide.exception.BadRequestException;
import tourGuide.service.ITourGuideService;

/**
 * The Class TourGuideController.
 */
@RestController
public class TripDealsController {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(TripDealsController.class);


	/** The tour guide service. */
	@Autowired
	ITourGuideService tourGuideService;


	// ##############################################################


    /**
     * Instantiates a new tour guide controller.
     *
     * @param tourGuideService the tour guide service
     */
    public TripDealsController(
    		final ITourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }



	//###############################################################




    /**
	 * Gets the user trip deals.
	 *
	 * @param userName the user name
	 * @return the user trip deals
	 */
	@RequestMapping("/getTripDeals")
    public List<ProviderDTO> getUserTripDeals(
    		@RequestParam final String userName) {

        logger.info("## getTripDeals"
        		+ " for user {} : ", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        List<ProviderDTO> userTripDeals = tourGuideService
        		.getUserTripDeals(userName);


        logger.info("## providers {} "
        		+ " for user {} : ", userTripDeals.size(), userName);

        return userTripDeals;
        
    }


	//###############################################################
    



}