package tourGuide.model;

//import gpsUtil.location.Attraction;
//import gpsUtil.location.VisitedLocation;
import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;

/**
 * The Class UserReward.
 */
public class UserReward {

	/** The visited location. */
	public final VisitedLocation visitedLocation;

	/** The attraction. */
	public final Attraction attraction;

	/** The reward points. */
	private int rewardPoints;



	// ##############################################################


	/**
	 * Instantiates a new user reward.
	 *
	 * @param visitedLocation the visited location
	 * @param attraction the attraction
	 * @param rewardPoints the reward points
	 */
	public UserReward(
			VisitedLocation visitedLocation,
			Attraction attraction,
			int rewardPoints) {

		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
		this.rewardPoints = rewardPoints;
	}



	// ##############################################################


	/**
	 * Instantiates a new user reward.
	 *
	 * @param visitedLocation the visited location
	 * @param attraction the attraction
	 */
	public UserReward(VisitedLocation visitedLocation, Attraction attraction) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
	}

	/**
	 * Sets the reward points.
	 *
	 * @param rewardPoints the new reward points
	 */
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	/**
	 * Gets the reward points.
	 *
	 * @return the reward points
	 */
	public int getRewardPoints() {
		return rewardPoints;
	}



	// ##############################################################


}
