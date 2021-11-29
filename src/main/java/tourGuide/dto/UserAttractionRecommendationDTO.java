package tourGuide.dto;

import java.util.Map;

import com.jsoniter.annotation.JsonProperty;

import gpsUtil.location.Location;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class UserAttractionRecommendationDTO.
 */
public class UserAttractionRecommendationDTO {


	/** The user position. */
	@Getter @Setter
    @JsonProperty("userPosition")
    Location userPosition;

	/** The nearby attraction DT os. */
	@Getter @Setter
	@JsonProperty("nearbyAttractions")
    Map<String, NearbyAttractionDTO> nearbyAttractionDTOs;



	// ##############################################################


    /**
	 * Instantiates a new user attraction recommendation DTO.
	 *
	 * @param userPosition the user position
	 * @param nearbyAttractionDTOs the nearby attraction DT os
	 */
	public UserAttractionRecommendationDTO(
    		Location userPosition,
    		Map<String, NearbyAttractionDTO> nearbyAttractionDTOs) {

    	this.userPosition = userPosition;
        this.nearbyAttractionDTOs = nearbyAttractionDTOs;
    }



	// ##############################################################


}
