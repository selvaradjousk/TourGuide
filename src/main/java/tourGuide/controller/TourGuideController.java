package tourGuide.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.dto.LocationDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.exception.BadRequestException;
import tourGuide.model.User;
import tourGuide.service.ITourGuideService;

/**
 * The Class TourGuideController.
 */
@RestController
public class TourGuideController {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(TourGuideController.class);


	/** The tour guide service. */
	@Autowired
	ITourGuideService tourGuideService;


	// ##############################################################


    /**
     * Instantiates a new tour guide controller.
     *
     * @param tourGuideService the tour guide service
     */
    public TourGuideController(
    		final ITourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }



	//###############################################################


	
	

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@RequestMapping("/")
    public String index() {

        logger.info("## index() page requested");

		return "Greetings from TourGuide!";
    }




	//###############################################################


	
	
    /**
	 * Gets the location.
	 *
	 * @param userName the user name
	 * @return the location
	 * @throws BadRequestException the bad request exception
	 */
	@RequestMapping("/getLocation") 
    public LocationDTO getLocation(
    		@Valid @RequestParam String userName)
    				throws BadRequestException {

        logger.info("## getLocation() page requested"
        		+ " for user {} : ", userName);


        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        LocationDTO userLocation = tourGuideService.getUserLocation(userName);

      logger.info("## visitedLocation {} "
		+ " for user {} : ", userLocation, userName);
        return userLocation;
    }




	//###############################################################





    /**
	 * Gets the user recommended attractions.
	 *
	 * @param userName the user name
	 * @return the user recommended attractions
	 */
	@RequestMapping("/getNearbyAttractions")
    public UserAttractionRecommendationDTO getUserRecommendedAttractions(
    		@RequestParam final String userName) {

        logger.info("## getNearbyAttractions() page requested"
        		+ " for user {} : ", userName);


        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        UserAttractionRecommendationDTO nearByAttractions = tourGuideService
        		.getUserAttractionRecommendation(userName);
        
        return nearByAttractions;
    }





	//###############################################################





    /**
	 * Gets the user rewards.
	 *
	 * @param userName the user name
	 * @return the user rewards
	 */
	@RequestMapping("/getRewards") 
    public int getUserRewards(
    		@RequestParam final String userName) {

        logger.info("## getRewards for user {} requested", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        int userRewards = tourGuideService.getTotalRewardPointsForUser(userName);

        logger.info("## getRewards for user rewardPointsTotalValue"
        		+ " {} requested", userRewards);

        return userRewards;
    }

//
//    @RequestMapping("/getRewards") 
//    public List<UserRewardDTO> getUserRewards(
//    		@RequestParam final String userName) {
//
//        logger.info("## getRewards for user {} requested", userName);
//
//        if (userName.length() == 0) {
//            throw new BadRequestException("username is required");
//        }
//        List<UserRewardDTO> userRewards = tourGuideService
//        		.getUserRewards(userName);
//
//        logger.info("## getRewards for user rewards size"
//        		+ " {} requested", userRewards.size());
//
//        return userRewards;
//    }


	//###############################################################





    /**
 * Gets the users recent location.
 *
 * @return the users recent location
 */
@RequestMapping("/getAllCurrentLocations")
    public Map<String, LocationDTO> getUsersRecentLocation() {

        logger.info("## getAllCurrentLocations requested");

        Map<String, LocationDTO> usersRecentLocation = tourGuideService
        		.getAllUserRecentLocation();
        
        return usersRecentLocation;
    }




	//###############################################################





    /**
	 * Gets the user trip deals.
	 *
	 * @param userName the user name
	 * @return the user trip deals
	 */
	@RequestMapping("/getTripDeals")
    public List<ProviderDTO> getUserTripDeals(
    		@RequestParam final String userName) {

        logger.info("## getTripDeals"
        		+ " for user {} : ", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        List<ProviderDTO> userTripDeals = tourGuideService
        		.getUserTripDeals(userName);


        logger.info("## providers {} "
        		+ " for user {} : ", userTripDeals.size(), userName);

        return userTripDeals;
        
    }


	//###############################################################
    


    /**
	 * Gets the user preferences.
	 *
	 * @param userPreferencesDTO the user preferences DTO
	 * @param userName the user name
	 * @return the user preferences
	 */
	@PutMapping("/updateUserPreferences")
    @ResponseStatus(HttpStatus.CREATED)
    public UserPreferencesDTO getUserPreferences(
    		@Valid @RequestBody final UserPreferencesDTO userPreferencesDTO,
            @RequestParam final String userName) {
    	

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        UserPreferencesDTO userPreferences = tourGuideService
        		.updateUserPreferences(userName, userPreferencesDTO);

        return userPreferences;

    }



	//###############################################################



    /**
	 * Gets the user.
	 *
	 * @param userName the user name
	 * @return the user
	 * @throws BadRequestException the bad request exception
	 */
	@RequestMapping("/getUser")
    private User getUser(String userName) throws BadRequestException  {

        logger.info("## getUser "
        		+ " for user {} : ", userName);

    	return tourGuideService.getUser(userName);
    }



	//###############################################################





}