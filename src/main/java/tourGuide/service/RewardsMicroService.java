package tourGuide.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;

public class RewardsMicroService implements IRewardsMicroService {


	private Logger logger = LoggerFactory
			.getLogger(RewardsMicroService.class);

    private final RewardCentral rewardCentral;


	// ##############################################################


    @Autowired
    public RewardsMicroService(
    		final GpsUtil gpsUtil,
    		final RewardCentral rewardCentral) {

    	this.rewardCentral = rewardCentral;

    }


	// ##############################################################

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
