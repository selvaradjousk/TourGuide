package tourGuide.service;

import java.util.List;

import tourGuide.dto.UserRewardDTO;
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

	/**
	 * Gets the user rewards.
	 *
	 * @param userName the user name
	 * @return the user rewards
	 */
	List<UserRewardDTO> getUserRewards(String userName);

}