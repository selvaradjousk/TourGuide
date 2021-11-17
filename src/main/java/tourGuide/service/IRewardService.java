package tourGuide.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import tourGuide.model.User;

public interface IRewardService {

	void calculateRewards(User user);

	boolean isWithinAttractionProximity(
			Attraction attraction,
			Location location);

}