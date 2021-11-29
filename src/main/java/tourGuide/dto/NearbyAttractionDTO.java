package tourGuide.dto;

import com.jsoniter.annotation.JsonProperty;

import lombok.Getter;

public class NearbyAttractionDTO {

	/** The latitude. */
	@Getter
    @JsonProperty("latitude")
    double latitude;

	/** The longitude. */
	@Getter
	@JsonProperty("longitude")
	double longitude;

	/** The distance. */
	@JsonProperty("distance")
    @Getter
    double distance;

	/** The reward points. */
	@JsonProperty("rewardPoints")
    @Getter
    int rewardPoints;



	// ##############################################################


    /**
	 * Instantiates a new nearby attraction DTO.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 * @param rewardPoints the reward points
	 */
	public NearbyAttractionDTO(
    		double latitude,
    		double longitude,
    		double distance,
    		int rewardPoints) {

    	this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.rewardPoints = rewardPoints;
    }




	// ##############################################################



}
