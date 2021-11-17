package tourGuide.dto;

import java.util.Map;

import com.jsoniter.annotation.JsonProperty;

import gpsUtil.location.Location;
import lombok.Getter;
import lombok.Setter;

public class UserAttractionRecommendation {


	@Getter @Setter
    @JsonProperty("userPosition")
    Location userPosition;

	@Getter @Setter
	@JsonProperty("nearbyAttractions")
    Map<String, NearbyAttraction> nearbyAttractions;



	// ##############################################################


    public UserAttractionRecommendation(
    		Location userPosition,
    		Map<String, NearbyAttraction> nearbyAttractions) {

    	this.userPosition = userPosition;
        this.nearbyAttractions = nearbyAttractions;
    }



	// ##############################################################


}
