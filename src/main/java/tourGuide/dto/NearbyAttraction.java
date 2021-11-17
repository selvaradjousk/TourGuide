package tourGuide.dto;

import com.jsoniter.annotation.JsonProperty;

import lombok.Getter;

public class NearbyAttraction {

	@Getter
    @JsonProperty("latitude")
    double latitude;

	@Getter
	@JsonProperty("longitude")
	double longitude;
    @JsonProperty("distance")

    @Getter
    double distance;
    @JsonProperty("rewardPoints")

    @Getter
    int rewardPoints;



	// ##############################################################


    public NearbyAttraction(
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
