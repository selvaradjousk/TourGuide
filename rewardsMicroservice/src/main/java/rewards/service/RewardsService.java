package rewards.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

/**
 * The Class RewardsService.
 */
@Service
public class RewardsService implements IRewardsService {


	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(RewardsService.class);


	 /** The reward central. */
 	private final RewardCentral rewardCentral;



	// ##############################################################

	/**
	 * Instantiates a new rewards service.
	 *
	 * @param rewardCentral the reward central
	 */
	public RewardsService(RewardCentral rewardCentral) {
		super();
		this.rewardCentral = rewardCentral;
	}




	// ##############################################################



    /**
	 * Gets the attraction reward points.
	 *
	 * @param attractionId the attraction id
	 * @param userId the user id
	 * @return the attraction reward points
	 */
	public int getAttractionRewardPoints(
    		final UUID attractionId,
    		final UUID userId) {

    	logger.debug("RewardsService.getAttractionRewardPoints called");

        int rewardPoints = rewardCentral.getAttractionRewardPoints(
        		attractionId,
        		userId);

        return rewardPoints;
    }



	// ##############################################################



}
