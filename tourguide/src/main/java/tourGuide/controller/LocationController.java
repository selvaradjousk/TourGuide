package tourGuide.controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.dto.LocationDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.exception.BadRequestException;
import tourGuide.service.ITourGuideService;

/**
 * The Class TourGuideController.
 */
@RestController
public class LocationController {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(LocationController.class);


	/** The tour guide service. */
	@Autowired
	ITourGuideService tourGuideService;


	// ##############################################################


    /**
     * Instantiates a new tour guide controller.
     *
     * @param tourGuideService the tour guide service
     */
    public LocationController(
    		final ITourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
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




}