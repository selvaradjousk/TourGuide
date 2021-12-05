package tourGuide.util;

import org.springframework.stereotype.Component;
import gpsUtil.location.Attraction;
import tourGuide.dto.AttractionDTO;
import tourGuide.model.Location;

@Component
public class AttractionMapper {

	// ##############################################################

    public AttractionDTO toAttractionDTO(
    		final Attraction attraction) {

        return new AttractionDTO(
        		attraction.attractionId,
        		attraction.attractionName,
        		attraction.city,
                attraction.state,
                new Location(
                		attraction.longitude,
                		attraction.latitude));
    }

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
