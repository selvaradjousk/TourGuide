package gps.service;

import java.util.List;
import java.util.UUID;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;


/**
 * The Interface IGpsService.
 */
public interface IGpsService {

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
