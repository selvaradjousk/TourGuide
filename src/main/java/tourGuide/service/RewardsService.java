package tourGuide.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.model.User;
import tourGuide.model.UserReward;

@Service
public class RewardsService implements IRewardService {



	private Logger logger = LoggerFactory
			.getLogger(RewardsService.class);

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE
													= 1.15077945;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsUtil gpsUtil;
	private final RewardCentral rewardsCentral;



	// ##############################################################


	public RewardsService(
			GpsUtil gpsUtil,
			RewardCentral rewardCentral) {

		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}



	// ##############################################################


	public void setProximityBuffer(int proximityBuffer) {

		logger.info("## setProximityBuffer() called"
				+ " : {}", proximityBuffer);

		this.proximityBuffer = proximityBuffer;

	}



	// ##############################################################


	public void setDefaultProximityBuffer() {

		logger.info("## setDefaultProximityBuffer() called");

		proximityBuffer = defaultProximityBuffer;

	}



	// ##############################################################


	@Override
	public void calculateRewards(User user) {

		logger.info("## calculateRewards() for user {}"
				+ " called: ", user.getUserName());

		List<VisitedLocation> userLocations = user.getVisitedLocations();

		logger.info("## userLocations() for user {} : {}"
				+ " ", user.getUserName(), userLocations);

		List<Attraction> attractions = gpsUtil.getAttractions();

		logger.info("## attractions() for user {} : {}"
				+ " ", user.getUserName(), attractions);

		for(VisitedLocation visitedLocation : userLocations) {

			for(Attraction attraction : attractions) {
				if(user
						.getUserRewards()
						.stream()
						.filter(r -> r.attraction.attractionName.equals(
								attraction.attractionName)).count() == 0) {

					if(nearAttraction(visitedLocation, attraction)) {

						user.addUserReward(
								new UserReward(
										visitedLocation,
										attraction,
										getRewardPoints(attraction, user)));

					}
				}
			}

			logger.info("## userRewards() for user {} : {}"
					+ " ", user.getUserName(), user.getUserRewards());

		}
	}



	// ##############################################################


	public boolean isWithinAttractionProximity(
			Attraction attraction,
			Location location) {

		logger.info("## isWithinAttractionProximity()"
				+ " for attraction {} & location {} called ", attraction, location);

		return getDistance(attraction, location)
				> attractionProximityRange ?
						false
						: true;

	}



	// ##############################################################


	private boolean nearAttraction(
			VisitedLocation visitedLocation,
			Attraction attraction) {

		logger.info("## nearAttraction()"
				+ " for attraction {} & visitedLocation"
				+ " {} called ", attraction, visitedLocation);

		return getDistance(
				attraction,
				visitedLocation.location)
				> proximityBuffer ?
						false
						: true;
	}



	// ##############################################################


	private int getRewardPoints(
			Attraction attraction,
			User user) {

		logger.info("## getRewardPoints()"
				+ " for attraction {} & user {} called ", attraction, user.getUserName());

		return rewardsCentral
				.getAttractionRewardPoints(
						attraction.attractionId,
						user.getUserId());

	}



	// ##############################################################


	public double getDistance(Location loc1, Location loc2) {

		logger.info("## getDistance location1 {}"
				+ " & location2 {} called: ",
				loc1, loc2);

		double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        logger.info("## latitude1: {}", lat1);
        logger.info("## latitude2: {}", lat2);
        logger.info("## longitude1: {}", lon1);
        logger.info("## longitude2: {}", lon2);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1)
                               * Math.cos(lat2)
                               * Math.cos(lon1 - lon2));

        logger.info("## angle: {}", angle);

        double nauticalMiles = 60 * Math.toDegrees(angle);

        logger.info("## nauticalMiles: {}", nauticalMiles);

        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE
        		* nauticalMiles;

        logger.info("## statuteMiles: {}", statuteMiles);

        return statuteMiles;
	}



	// ##############################################################


}
