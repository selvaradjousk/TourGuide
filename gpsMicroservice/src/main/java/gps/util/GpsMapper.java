package gps.util;

import org.springframework.stereotype.Component;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.model.Location;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;


/**
 * The Class GpsMapper.
 */
@Component
public class GpsMapper {

	
    /**
     * Instantiates a new gps mapper.
     */
    public GpsMapper() {
    }

	// ##############################################################


    /**
	 * To attraction DTO.
	 *
	 * @param attraction the attraction
	 * @return the attraction DTO
	 */
	public AttractionDTO toAttractionDTO(
    		final Attraction attraction) {

        return new AttractionDTO(
        		attraction.attractionId,
        		attraction.attractionName,
        		attraction.city,
                attraction.state,
                new Location(attraction.latitude, attraction.longitude));
    }



	// ##############################################################


    /**
	 * To visited location DTO.
	 *
	 * @param visitedLocation the visited location
	 * @return the visited location DTO
	 */
	public VisitedLocationDTO toVisitedLocationDTO(
    		final VisitedLocation visitedLocation) {

        return new VisitedLocationDTO(
        		visitedLocation.userId,
        		new Location(
        				visitedLocation.location.latitude,
        				visitedLocation.location.longitude),
        		visitedLocation.timeVisited);
    }


	// ##############################################################


}
