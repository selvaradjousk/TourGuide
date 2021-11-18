package tourGuide.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.model.UserPreferences;

@NoArgsConstructor
@Getter
@Setter
public class UserPreferencesDTO {
	
	    private String username;
	    private Integer attractionProximity;
	    private String currency;
	    private Integer lowerPricePoint;
	    private Integer highPricePoint;
	    private Integer tripDuration;
	    private Integer ticketQuantity;
	    private Integer numberOfAdults;
	    private Integer numberOfChildren;



		// ##############################################################


	    public UserPreferencesDTO(
	    		String username,
	            int attractionProximity,
	            int lowerPricePoint,
	            int highPricePoint,
	            int tripDuration,
	            int ticketQuantity,
	            int numberOfAdults,
	            int numberOfChildren) {

	    	this.username = username;
			this.attractionProximity = attractionProximity;
			this.lowerPricePoint = lowerPricePoint;
			this.highPricePoint = highPricePoint;
			this.tripDuration = tripDuration;
			this.ticketQuantity = ticketQuantity;
			this.numberOfAdults = numberOfAdults;
			this.numberOfChildren = numberOfChildren;
	}


		// ##############################################################

	
	public UserPreferencesDTO(
			String username,
			UserPreferences userPreferences) {

			this.username = username;
			this.attractionProximity = userPreferences.getAttractionProximity();
			this.lowerPricePoint = userPreferences.getLowerPricePoint().getNumber().intValueExact();
			this.highPricePoint = userPreferences.getHighPricePoint().getNumber().intValueExact();
			this.tripDuration = userPreferences.getTripDuration();
			this.ticketQuantity = userPreferences.getTicketQuantity();
			this.numberOfAdults = userPreferences.getNumberOfAdults();
			this.numberOfChildren = userPreferences.getNumberOfChildren();
	}


	// ##############################################################


}
