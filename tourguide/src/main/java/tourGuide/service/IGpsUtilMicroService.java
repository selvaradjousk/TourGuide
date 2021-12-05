package tourGuide.service;

import java.util.List;
import java.util.UUID;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.VisitedLocationDTO;

/**
 * The Interface IGpsUtilMicroService.
 */
public interface IGpsUtilMicroService {

	/**
	 * Gets the user location.
	 *
	 * @param userId the user id
	 * @return the user location
	 */
	VisitedLocationDTO getUserLocation(UUID userId);

	
	/**
	 * Gets the attractions.
	 *
	 * @return the attractions
	 */
	List<AttractionDTO> getAttractions();
}
