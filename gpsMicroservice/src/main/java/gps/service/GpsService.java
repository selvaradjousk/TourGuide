package gps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.exception.UserNotFoundException;
import gps.util.GpsMapper;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;


/**
 * The Class GpsService.
 */
@Service
public class GpsService implements IGpsService {

	/** The logger. */
//	private Logger logger = LoggerFactory
//			.getLogger(GpsService.class);

    /**
     * GpsUtil instance.
     */
    private final GpsUtil gpsUtil;

    /** The gps mapper. */
    private final GpsMapper gpsMapper;


	// ##############################################################


	/**
	 * Instantiates a new gps service.
	 *
	 * @param gpsUtil the gps util
	 * @param gpsMapper the gps mapper
	 */
	public GpsService(GpsUtil gpsUtil, GpsMapper gpsMapper) {
		super();
		this.gpsUtil = gpsUtil;
		this.gpsMapper = gpsMapper;
	}


	// ##############################################################


    /**
	 * Gets the user location.
	 *
	 * @param userId the user id
	 * @return the user location
	 */
	public VisitedLocationDTO getUserLocation(final UUID userId) {

//    	logger.debug("MicroService:GpsService.getUserLocation called");

    	if (userId == null) {
            throw new UserNotFoundException("Username not found");
        }
        VisitedLocation visitedLocation = gpsUtil
        		.getUserLocation(userId);

        return gpsMapper.toVisitedLocationDTO(visitedLocation);
    }


	// ##############################################################


    /**
	 * Gets the attractions.
	 *
	 * @return the attractions
	 */
	public List<AttractionDTO> getAttractions() {

//    	logger.debug("MicroService:GpsService.getAttractions called");

        List<AttractionDTO> attractionList = new ArrayList<>();
        List<Attraction> attractions = gpsUtil.getAttractions();

        attractions.forEach(attraction -> {
            attractionList.add(
            		gpsMapper.toAttractionDTO(attraction));
        });

        return attractionList;
    }



	// ##############################################################


}
