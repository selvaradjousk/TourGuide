package tourGuide.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;

/**
 * The Class RewardsMicroService.
 */
@Service
public class RewardsMicroService implements IRewardsMicroService {


	private Logger logger = LoggerFactory
			.getLogger(RewardsMicroService.class);

    private final RewardCentral rewardCentral;


	// ##############################################################


    /**
	 * Instantiates a new rewards micro service.
	 *
	 * @param gpsUtil the gps util
	 * @param rewardCentral the reward central
	 */
	@Autowired
    public RewardsMicroService(
    		final GpsUtil gpsUtil,
    		final RewardCentral rewardCentral) {

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
	@Override
	public int getAttractionRewardPoints(
			final UUID attractionId,
			final UUID userId) {

		logger.info("## getAttractionRewardPoints() called");


        int rewardPoints = rewardCentral
        		.getAttractionRewardPoints(attractionId, userId);

		logger.info("## rewardPoints: {}"
				+ " returned", rewardPoints);

        return rewardPoints;
    }

	// ##############################################################

}
