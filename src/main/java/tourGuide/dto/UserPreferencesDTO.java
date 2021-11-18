package tourGuide.dto;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.constant.Constraints;
import tourGuide.model.UserPreferences;

@NoArgsConstructor
@Getter
@Setter
public class UserPreferencesDTO {

//	@Length(max = Constraints.USERNAME_MIN_LENGTH,
//	    	message = "Username cannot be empty")
	private String username;

	@Min(value = Constraints.PROXIMITY_LOWER_LIMIT,
			message = "Valid value required")
	private Integer attractionProximity;


	private String currency;

	@Min(value = Constraints.LOWERPRICEPOINT_VALUE,
			message = "Valid value required")
	private Integer lowerPricePoint;

	@Min(value = Constraints.HIGHPRICEPOINT_VALUE,
			message = "Valid value required")
	private Integer highPricePoint;

	@Min(value = Constraints.TRIP_DURATION_MIN_VALUE,
			message = "Minimum stay period should be atleast 1")
	private Integer tripDuration;

	@Min(value = Constraints.TICKET_QUANTITY_MIN_VALUE,
			message = "Minimum ticket quantity value (1) required")
	private Integer ticketQuantity;

	@Min(value = Constraints.ADULT_NUMBER_MIN_VALUE,
			message = "Minimum number of adults (1) required")
	private Integer numberOfAdults;

	@Min(value = Constraints.CHILD_NUMBER_MIN_VALUE,
			message = "Valid value required")
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
