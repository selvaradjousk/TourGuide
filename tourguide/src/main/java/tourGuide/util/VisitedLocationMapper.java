package tourGuide.util;

import org.springframework.stereotype.Component;

import gpsUtil.location.VisitedLocation;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.model.Location;

/**
 * The Class VisitedLocationMapper.
 */
@Component
public class VisitedLocationMapper {

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


    /**
	 * To visited location.
	 *
	 * @param visitedLocationDTO the visited location DTO
	 * @return the tour guide.model. visited location
	 */
	public tourGuide.model.VisitedLocation toVisitedLocation(
    		final VisitedLocationDTO visitedLocationDTO) {

      return new tourGuide.model.VisitedLocation(
    		  visitedLocationDTO.getUserId(),
    		  visitedLocationDTO.getLocation(),
    		  visitedLocationDTO.getTimeVisited());
	}



	// ##############################################################


}
