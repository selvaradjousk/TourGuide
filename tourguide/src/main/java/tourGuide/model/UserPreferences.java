package tourGuide.model;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class UserPreferences.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserPreferences {

	/** The attraction proximity. */
	private int attractionProximity = Integer.MAX_VALUE;

	/** The currency. */
	private CurrencyUnit currency = Monetary
			.getCurrency("USD");

	/** The lower price point. */
	private Money lowerPricePoint = Money.of(0, currency);

	/** The high price point. */
	private Money highPricePoint = Money.of(
			Integer.MAX_VALUE,
			currency);

	/** The trip duration. */
	private int tripDuration = 1;

	/** The ticket quantity. */
	private int ticketQuantity = 1;

	/** The number of adults. */
	private int numberOfAdults = 1;

	/** The number of children. */
	private int numberOfChildren = 0;



	// ##############################################################

    /**
	 * Instantiates a new user preferences.
	 *
	 * @param attractionProximity the attraction proximity
	 * @param lowerPricePoint the lower price point
	 * @param highPricePoint the high price point
	 * @param tripDuration the trip duration
	 * @param ticketQuantity the ticket quantity
	 * @param numberOfAdults the number of adults
	 * @param numberOfChildren the number of children
	 */
	public UserPreferences(
    		final int attractionProximity,
    		final Money lowerPricePoint,
    		final Money highPricePoint,
            final int tripDuration,
            final int ticketQuantity,
            final int numberOfAdults,
            final int numberOfChildren) {

    	this.attractionProximity = attractionProximity;
		this.lowerPricePoint = lowerPricePoint;
		this.highPricePoint = highPricePoint;
		this.tripDuration = tripDuration;
		this.ticketQuantity = ticketQuantity;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;

    }



	// ##############################################################

}
