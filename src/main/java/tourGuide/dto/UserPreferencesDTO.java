package tourGuide.dto;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.constant.Constraints;
import tourGuide.model.UserPreferences;

/**
 * The Class UserPreferencesDTO.
 */
@NoArgsConstructor
@Getter
@Setter
public class UserPreferencesDTO {

//	@Length(max = Constraints.USERNAME_MIN_LENGTH,
//	    	message = "Username cannot be empty")

	/** The username. */
	private String username;

	/** The attraction proximity. */
	@Min(value = Constraints.PROXIMITY_LOWER_LIMIT,
			message = "Valid value required")
	private Integer attractionProximity;


	/** The currency. */
	private String currency;

	/** The lower price point. */
	@Min(value = Constraints.LOWERPRICEPOINT_VALUE,
			message = "Valid value required")
	private Integer lowerPricePoint;

	/** The high price point. */
	@Min(value = Constraints.HIGHPRICEPOINT_VALUE,
			message = "Valid value required")
	private Integer highPricePoint;

	/** The trip duration. */
	@Min(value = Constraints.TRIP_DURATION_MIN_VALUE,
			message = "Minimum stay period should be atleast 1")
	private Integer tripDuration;

	/** The ticket quantity. */
	@Min(value = Constraints.TICKET_QUANTITY_MIN_VALUE,
			message = "Minimum ticket quantity value (1) required")
	private Integer ticketQuantity;

	/** The number of adults. */
	@Min(value = Constraints.ADULT_NUMBER_MIN_VALUE,
			message = "Minimum number of adults (1) required")
	private Integer numberOfAdults;

	/** The number of children. */
	@Min(value = Constraints.CHILD_NUMBER_MIN_VALUE,
			message = "Valid value required")
	private Integer numberOfChildren;



		// ##############################################################


	    /**
		 * Instantiates a new user preferences DTO.
		 *
		 * @param username the username
		 * @param attractionProximity the attraction proximity
		 * @param lowerPricePoint the lower price point
		 * @param highPricePoint the high price point
		 * @param tripDuration the trip duration
		 * @param ticketQuantity the ticket quantity
		 * @param numberOfAdults the number of adults
		 * @param numberOfChildren the number of children
		 */
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

	
	/**
		 * Instantiates a new user preferences DTO.
		 *
		 * @param username the username
		 * @param userPreferences the user preferences
		 */
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
