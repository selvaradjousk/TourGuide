package tourGuide.helper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;


/**
 * The Class InternalTestHelper.
 */
@Component
public class InternalTestHelper {

	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(InternalTestHelper.class);




	// ##############################################################



	/** ********  Methods Below: For Internal Testing  ********/

	// Set this default up to 100,000 for testing
    @Value("${test.user.numbers}")
    private int internalUserNumber;

    /** The trip pricer api key. */
    // Mandatory API Key to use TripPricer.
    @Value("${trip.pricer.api.key}")
    private String TRIP_PRICER_API_KEY;


    /**
     * Gets the trip pricer api key.
     *
     * @return the trip pricer api key
     */
    public String getTripPricerApiKey() {
        return TRIP_PRICER_API_KEY;
    }
    
    
	// Database connection will be used for external users,
	// but for testing purposes internal users are provided
	// and stored in memory
    
    /** The internal user map. */
	public final Map<String, User> internalUserMap = new HashMap<>();



	// ##############################################################




	/**
	 * Initialize internal users.
	 */
	public void initializeInternalUsers() {

		IntStream.range(
				0,
				internalUserNumber)
		.forEach(i -> {

			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";

			User user = new User(
					UUID.randomUUID(),
					userName,
					phone,
					email);

			generateUserLocationHistory(user);
			
			internalUserMap.put(userName, user);

		});

		logger.debug("Created"
				+ " " + internalUserNumber
				+ " internal test users.");
	}



	// ##############################################################


	
	/**
	 * Generate user location history.
	 *
	 * @param user the user
	 */
	private void generateUserLocationHistory(User user) {

		IntStream.range(0, 3).forEach(i-> {

			user.addToVisitedLocations(
					new VisitedLocation(
							user.getUserId(),
							new Location(
									generateRandomLatitude(),
									generateRandomLongitude()),
							getRandomTime()));

		});
	}



	// ##############################################################



	/**
	 * Generate random longitude.
	 *
	 * @return the double
	 */
	private double generateRandomLongitude() {

		double leftLimit = -180;
	    double rightLimit = 180;

	    return leftLimit
	    		+ new Random().nextDouble()
	    		* (rightLimit - leftLimit);
	}



	// ##############################################################



	/**
	 * Generate random latitude.
	 *
	 * @return the double
	 */
	private double generateRandomLatitude() {

		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;

	    return leftLimit
	    		+ new Random().nextDouble()
	    		* (rightLimit - leftLimit);
	}



	// ##############################################################



	/**
	 * Gets the random time.
	 *
	 * @return the random time
	 */
	private Date getRandomTime() {

		LocalDateTime localDateTime = LocalDateTime.now()
				.minusDays(new Random()
						.nextInt(30));

		return Date.from(localDateTime
				.toInstant(ZoneOffset.UTC));
	}



	// ##############################################################


    /**
	 * Gets the internal user map.
	 *
	 * @return the internal user map
	 */
	public Map<String, User> getInternalUserMap() {
        return internalUserMap;
    }


	// ##############################################################


}
