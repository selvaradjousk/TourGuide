package tourGuide.service;

import java.util.List;
import java.util.UUID;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.VisitedLocationDTO;

public interface IGpsUtilMicroService {

	VisitedLocationDTO getUserLocation(UUID userId);

	
	List<AttractionDTO> getAttractions();
}
