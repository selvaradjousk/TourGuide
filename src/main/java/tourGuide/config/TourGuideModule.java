package tourGuide.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;
import tourGuide.service.IRewardService;
import tourGuide.service.RewardsService;

@Configuration
public class TourGuideModule {


	private Logger logger = LoggerFactory
			.getLogger(TourGuideModule.class);


	// ##############################################################


	@Bean
	public GpsUtil getGpsUtil() {

        logger.info("## getGpsUtil() BEAN invoked");

		return new GpsUtil();
	}



	// ##############################################################

	
	@Bean
	public IRewardService getRewardsService() {

        logger.info("## getRewardsService() BEAN invoked");

		return new RewardsService(getGpsUtil(), getRewardCentral());
	}



	// ##############################################################


	@Bean
	public RewardCentral getRewardCentral() {

        logger.info("## getRewardCentral() BEAN invoked");

		return new RewardCentral();
	}



	// ##############################################################


}
