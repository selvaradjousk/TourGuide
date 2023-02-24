package tourGuide.dto;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.constant.Constraints;

/**
 * The Class UserPreferencesDTO.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesDTO {


	/** The attraction proximity. */
	@Min(value = Constraints.PROXIMITY_LOWER_LIMIT,
			message = "Valid value required")
	private int attractionProximity;

	/** The lower price point. */
	@Min(value = Constraints.LOWERPRICEPOINT_VALUE,
			message = "Valid value required")
	private int lowerPricePoint;

	/** The high price point. */
	@Min(value = Constraints.HIGHPRICEPOINT_VALUE,
			message = "Valid value required")
	private int highPricePoint;

	/** The trip duration. */
	@Min(value = Constraints.TRIP_DURATION_MIN_VALUE,
			message = "Minimum stay period should be atleast 1")
	private int tripDuration;

	/** The ticket quantity. */
	@Min(value = Constraints.TICKET_QUANTITY_MIN_VALUE,
			message = "Minimum ticket quantity value (1) required")
	private int ticketQuantity;

	/** The number of adults. */
	@Min(value = Constraints.ADULT_NUMBER_MIN_VALUE,
			message = "Minimum number of adults (1) required")
	private int numberOfAdults;

	/** The number of children. */
	@Min(value = Constraints.CHILD_NUMBER_MIN_VALUE,
			message = "Valid value required")
	private int numberOfChildren;



}
