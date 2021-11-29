package tourGuide.model;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;


/**
 * The Class UserPreferences.
 */
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
	 */
	public UserPreferences() {
	}



	// ##############################################################


	/**
	 * Sets the attraction proximity.
	 *
	 * @param attractionProximity the new attraction proximity
	 */
	public void setAttractionProximity(int attractionProximity) {
		this.attractionProximity = attractionProximity;
	}

	/**
	 * Gets the attraction proximity.
	 *
	 * @return the attraction proximity
	 */
	public int getAttractionProximity() {
		return attractionProximity;
	}

	/**
	 * Gets the lower price point.
	 *
	 * @return the lower price point
	 */
	public Money getLowerPricePoint() {
		return lowerPricePoint;
	}

	/**
	 * Sets the lower price point.
	 *
	 * @param lowerPricePoint the new lower price point
	 */
	public void setLowerPricePoint(Money lowerPricePoint) {
		this.lowerPricePoint = lowerPricePoint;
	}

	/**
	 * Gets the high price point.
	 *
	 * @return the high price point
	 */
	public Money getHighPricePoint() {
		return highPricePoint;
	}

	/**
	 * Sets the high price point.
	 *
	 * @param highPricePoint the new high price point
	 */
	public void setHighPricePoint(Money highPricePoint) {
		this.highPricePoint = highPricePoint;
	}

	/**
	 * Gets the trip duration.
	 *
	 * @return the trip duration
	 */
	public int getTripDuration() {
		return tripDuration;
	}

	/**
	 * Sets the trip duration.
	 *
	 * @param tripDuration the new trip duration
	 */
	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	/**
	 * Gets the ticket quantity.
	 *
	 * @return the ticket quantity
	 */
	public int getTicketQuantity() {
		return ticketQuantity;
	}

	/**
	 * Sets the ticket quantity.
	 *
	 * @param ticketQuantity the new ticket quantity
	 */
	public void setTicketQuantity(int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}

	/**
	 * Gets the number of adults.
	 *
	 * @return the number of adults
	 */
	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	/**
	 * Sets the number of adults.
	 *
	 * @param numberOfAdults the new number of adults
	 */
	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	/**
	 * Gets the number of children.
	 *
	 * @return the number of children
	 */
	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	/**
	 * Sets the number of children.
	 *
	 * @param numberOfChildren the new number of children
	 */
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}


	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public CurrencyUnit getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency the new currency
	 */
	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}

	// ##############################################################


}
