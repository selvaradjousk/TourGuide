package rewards.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rewards.service.IRewardsService;

/**
 * The Class RewardsController.
 */
@RestController
@RequestMapping("/rewards")
public class RewardsController {


	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(RewardsController.class);

    /** The rewards service. */
    private final IRewardsService rewardsService;

	// ##############################################################



	/**
	 * Instantiates a new rewards controller.
	 *
	 * @param rewardsService the rewards service
	 */
	public RewardsController(
			final IRewardsService rewardsService) {
		super();
		this.rewardsService = rewardsService;
	}



	// ##############################################################



    /**
	 * Gets the reward points.
	 *
	 * @param attractionId the attraction id
	 * @param userId the user id
	 * @return the reward points
	 */
	@GetMapping("/points/{attractionId}/{userId}")
    public int getRewardPoints(
    		@PathVariable final UUID attractionId,
    		@PathVariable final UUID userId) {

    	logger.debug("GET Requested: /rewards/points/{attractionId}"
        		+ "/{userId} for userId: {}", userId.toString());

        int rewardsPoints = rewardsService
        		.getAttractionRewardPoints(
							        		attractionId,
							        		userId);

        logger.debug("GET getRewardPoints execution OK");

        return rewardsPoints;
    }


	// ##############################################################



}
