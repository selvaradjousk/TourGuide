package tourGuide.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import tourGuide.dto.LocationDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.model.User;

/**
 * The Interface ITourGuideService.
 */
public interface ITourGuideService {

	/**
	 * Gets the user rewards.
	 *
	 * @param userName the user name
	 * @return the user rewards
	 */
    List<UserRewardDTO> getUserRewards(String userName);


    /**
	 * Gets the user location.
	 *
	 * @param userName the user name
	 * @return the user location
	 */
    LocationDTO getUserLocation(String userName);

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
	 * @param userName the user name
	 * @return the trip deals
	 */
    List<ProviderDTO> getUserTripDeals(String userName);

//	VisitedLocation trackUserLocation(User user);

//	/**
// * Gets the near by attractions.
// *
// * @param visitedLocation the visited location
// * @return the near by attractions
// */
//	List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

	/**
	 * Gets the user attraction recommendation.
	 *
	 * @param userName the user name
	 * @return the user attraction recommendation
	 */
    UserAttractionRecommendationDTO getUserAttractionRecommendation(
    		String userName);

	/**
	 * Gets the all users last location.
	 *
	 * @return the all users last location
	 */
//	HashMap<String, LocationDTO> getAllUsersLastLocation();
    Map<String, LocationDTO> getAllUserRecentLocation();

	/**
	 * Update user preferences.
	 *
	 * @param userName the user name
	 * @param userPreferences the user preferences
	 * @return true, if successful
	 */
	UserPreferencesDTO updateUserPreferences(
    		String userName,
    		UserPreferencesDTO userPreferences) ;


	/**
	 * Track user location.
	 *
	 * @param user the user
	 * @return the completable future
	 */
	CompletableFuture<?> trackUserLocation(User user);


	int getTotalRewardPointsForUser(String userName);
}