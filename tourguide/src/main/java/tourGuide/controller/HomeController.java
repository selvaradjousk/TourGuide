package tourGuide.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class TourGuideController.
 */
@RestController
public class HomeController {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(HomeController.class);


	// ##############################################################



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



}