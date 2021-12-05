package tourGuide.dto;

import com.jsoniter.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tourGuide.model.Location;

@Getter
@Setter
@AllArgsConstructor
public class NearByAttractionDTO {

	@JsonProperty("attractionName")
    @Getter
    private String attractionName;

	@JsonProperty("attractionLocation")
    @Getter
    private Location attractionLocation;
    
	/** The distance. */
	@JsonProperty("distance")
    @Getter
    double distance;

	/** The reward points. */
	@JsonProperty("rewardPoints")
    @Getter
    int rewardPoints;

}
