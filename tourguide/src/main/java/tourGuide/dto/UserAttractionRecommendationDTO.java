package tourGuide.dto;

import java.util.List;

import com.jsoniter.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tourGuide.model.Location;

/**
 * The Class UserAttractionRecommendationDTO.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserAttractionRecommendationDTO {


	/** The user position. */
    @JsonProperty("userPosition")
    Location userPosition;

	/** The nearby attraction DTOs. */
	@JsonProperty("nearbyAttractions")
	private List<NearByAttractionDTO> nearbyAttractions;


}
