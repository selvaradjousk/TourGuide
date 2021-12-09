package tourGuide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.exception.BadRequestException;
import tourGuide.service.ITourGuideService;

/**
 * The Class TourGuideController.
 */
@RestController
public class RewardsController {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(RewardsController.class);


	/** The tour guide service. */
	@Autowired
	ITourGuideService tourGuideService;


	// ##############################################################


    /**
     * Instantiates a new tour guide controller.
     *
     * @param tourGuideService the tour guide service
     */
    public RewardsController(
    		final ITourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
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



}