package tourGuide.controller;

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

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.exception.BadRequestException;
import tourGuide.model.User;
import tourGuide.service.ITourGuideService;

/**
 * The Class TourGuideController.
 */
@RestController
public class UserController {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(UserController.class);


	/** The tour guide service. */
	@Autowired
	ITourGuideService tourGuideService;


	// ##############################################################


    /**
     * Instantiates a new tour guide controller.
     *
     * @param tourGuideService the tour guide service
     */
    public UserController(
    		final ITourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
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