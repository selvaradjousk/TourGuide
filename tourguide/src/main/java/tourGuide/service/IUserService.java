package tourGuide.service;

import java.util.List;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.User;

public interface IUserService {

	/**
	 * Gets the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	User getUser(String userName);

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	List<User> getAllUsers();

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	void addUser(User user);

	/**
	 * Update user preferences.
	 *
	 * @param userName the user name
	 * @param userPreferencesDTO the user preferences DTO
	 * @return the user preferences DTO
	 */
	UserPreferencesDTO updateUserPreferences(String userName, UserPreferencesDTO userPreferencesDTO);

}