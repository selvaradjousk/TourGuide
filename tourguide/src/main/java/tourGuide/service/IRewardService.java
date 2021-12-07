package tourGuide.service;

import tourGuide.model.User;

/**
 * The Interface IRewardService.
 */
public interface IRewardService {

	/**
	 * Calculate rewards.
	 *
	 * @param user the user
	 */
	void calculateRewards(User user);

//	/**
//	 * Checks if is within attraction proximity.
//	 *
//	 * @param attraction the attraction
//	 * @param location the location
//	 * @return true, if is within attraction proximity
//	 */
//	boolean isWithinAttractionProximity(
//			Attraction attraction,
//			Location location);
//
//	/**
//	 * Gets the distance.
//	 *
//	 * @param loc1 the loc 1
//	 * @param loc2 the loc 2
//	 * @return the distance
//	 */
//	double getDistance(Location loc1, Location loc2);
//
//	/**
//	 * Gets the reward points.
//	 *
//	 * @param attraction the attraction
//	 * @param user the user
//	 * @return the reward points
//	 */
//	int getRewardPoints(Attraction attraction,	User user);

}