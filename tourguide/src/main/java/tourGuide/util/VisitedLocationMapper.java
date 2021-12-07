package tourGuide.util;

import org.springframework.stereotype.Component;

import tourGuide.dto.VisitedLocationDTO;

/**
 * The Class VisitedLocationMapper.
 */
@Component
public class VisitedLocationMapper {

 



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
