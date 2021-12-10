package tourGuide.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import tourGuide.dto.LocationDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.model.User;

/**
 * The Interface ITourGuideService.
 */
public interface IGpsLocationService {



    /**
	 * Gets the user location.
	 *
	 * @param userName the user name
	 * @return the user location
	 */
    LocationDTO getUserLocation(String userName);


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
	 * Track user location.
	 *
	 * @param user the user
	 * @return the completable future
	 */
	CompletableFuture<?> trackUserLocation(User user);


	int getTotalRewardPointsForUser(String userName);


}