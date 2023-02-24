package tourGuide.util;

import org.springframework.stereotype.Component;

import tourGuide.dto.AttractionDTO;

@Component
public class AttractionMapper {


	// ##############################################################

    public tourGuide.model.Attraction toAttraction(
    		final AttractionDTO attractionDTO) {

        return new tourGuide.model.Attraction(
        		attractionDTO.getAttractionId(),
        		attractionDTO.getAttractionName(),
                attractionDTO.getCity(),
                attractionDTO.getState(),
                attractionDTO.getLocation());
    }   

	// ##############################################################

}
