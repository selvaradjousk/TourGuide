package gps.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.exception.UserNotFoundException;
import gps.service.IGpsService;

/**
 * The Class GpsController.
 */
@RestController
@RequestMapping("/gps")
public class GpsController {

	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(GpsController.class);

	   private IGpsService gpsService;


		// ##############################################################


	    @Autowired
	    public GpsController(
	    		final IGpsService gpsService) {
	        this.gpsService = gpsService;
	    }


		// ##############################################################


	    @GetMapping("/userLocation/{userId}")
	    public VisitedLocationDTO getUserLocation(
	    		@PathVariable("userId") final UUID userId) {

	        logger.info("## gps - Microservice getUserLocation URL called");

	        VisitedLocationDTO userLocation = gpsService.getUserLocation(userId);

	        if (userLocation == null) {
	            throw new UserNotFoundException("Failed to get user location");
	        }

	        return userLocation;
	    }


		// ##############################################################


	    @GetMapping("/attractions")
	    public List<AttractionDTO> getAttractions() {

	        logger.info("## gps - Microservice getAttractions URL called");

	        List<AttractionDTO> attractions = gpsService.getAttractions();

	        return attractions;
	    }



		// ##############################################################


}
