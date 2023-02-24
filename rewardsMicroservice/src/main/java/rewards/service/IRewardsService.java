package rewards.service;

import java.util.UUID;

/**
 * The Interface IRewardsService.
 */
public interface IRewardsService {

	/**
	 * Gets the attraction reward points.
	 *
	 * @param attractionId the attraction id
	 * @param userId the user id
	 * @return the attraction reward points
	 */
	int getAttractionRewardPoints(UUID attractionId, UUID userId);

}