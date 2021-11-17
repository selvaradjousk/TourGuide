package tourGuide.dto;

import java.util.Map;

import com.jsoniter.annotation.JsonProperty;

import gpsUtil.location.Location;
import lombok.Getter;
import lombok.Setter;

public class UserAttractionRecommendationDTO {


	@Getter @Setter
    @JsonProperty("userPosition")
    Location userPosition;

	@Getter @Setter
	@JsonProperty("nearbyAttractions")
    Map<String, NearbyAttractionDTO> nearbyAttractionDTOs;



	// ##############################################################


    public UserAttractionRecommendationDTO(
    		Location userPosition,
    		Map<String, NearbyAttractionDTO> nearbyAttractionDTOs) {

    	this.userPosition = userPosition;
        this.nearbyAttractionDTOs = nearbyAttractionDTOs;
    }



	// ##############################################################


}
