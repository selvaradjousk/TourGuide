package tourGuide.service;

import java.util.HashMap;
import java.util.List;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tripPricer.Provider;

/**
 * The Interface ITourGuideService.
 */
public interface ITourGuideService {

	/**
	 * Gets the user rewards.
	 *
	 * @param user the user
	 * @return the user rewards
	 */
	List<UserReward> getUserRewards(User user);

	/**
	 * Gets the user location.
	 *
	 * @param user the user
	 * @return the user location
	 */
	VisitedLocation getUserLocation(User user);

	/**
	 * Gets the all users last location.
	 *
	 * @return the all users last location
	 */
	HashMap<String, Location> getAllUsersLastLocation();

	/**
	 * Gets the user attraction recommendation.
	 *
	 * @param username the username
	 * @return the user attraction recommendation
	 */
	UserAttractionRecommendationDTO getUserAttractionRecommendation(
			String username);

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
	 * Gets the trip deals.
	 *
	 * @param user the user
	 * @return the trip deals
	 */
	List<Provider> getTripDeals(User user);

//	VisitedLocation trackUserLocation(User user);

	/**
 * Gets the near by attractions.
 *
 * @param visitedLocation the visited location
 * @return the near by attractions
 */
List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

	/**
	 * Update user preferences.
	 *
	 * @param userName the user name
	 * @param userPreferencesDTO the user preferences DTO
	 * @return true, if successful
	 */
	boolean updateUserPreferences(
    		String userName,
    		UserPreferencesDTO userPreferencesDTO) ;

}