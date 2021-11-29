package tourGuide.service;

import java.util.UUID;

/**
 * The Interface IRewardsMicroService.
 */
public interface IRewardsMicroService {

	/**
	 * Gets the attraction reward points.
	 *
	 * @param attractionId the attraction id
	 * @param userId the user id
	 * @return the attraction reward points
	 */
	int getAttractionRewardPoints(UUID attractionId, UUID userId);

}
