package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * The Class UserReward.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserReward {

	/** The visited location. */
	public final VisitedLocation visitedLocation;

	/** The attraction. */
	public final Attraction attraction;

	/** The reward points. */
	private int rewardPoints;


}
