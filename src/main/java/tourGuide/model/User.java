package tourGuide.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class User.
 */
@Getter
@Setter
@NoArgsConstructor
public class User {

	/** The user id. */
	private UUID userId;

	/** The user name. */
	@NotNull
	@Length(min=1, message = "USERNAME required")
	private String userName;

	/** The phone number. */
	private String phoneNumber;

	/** The email address. */
	private String emailAddress;

	/** The latest location timestamp. */
	private Date latestLocationTimestamp;

	/** The visited locations. */
	private List<VisitedLocation> visitedLocations
								= new ArrayList<>();

	/** The user rewards. */
	private List<UserReward> userRewards
								= new ArrayList<>();

	/** The user preferences. */
	private UserPreferences userPreferences
								= new UserPreferences();

	/** The trip deals. */
	private List<Provider> tripDeals
								= new ArrayList<>();



	// ##############################################################


	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user id
	 * @param userName the user name
	 * @param phoneNumber the phone number
	 * @param emailAddress the email address
	 */
	public User(
			UUID userId,
			String userName,
			String phoneNumber,
			String emailAddress) {

		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	// ##############################################################

	/**
	 * Adds the to visited locations.
	 *
	 * @param visitedLocation the visited location
	 */
	public void addToVisitedLocations(
			VisitedLocation visitedLocation) {
		visitedLocations.add(visitedLocation);
	}

	/**
	 * Clear visited locations.
	 */
	public void clearVisitedLocations() {
		visitedLocations.clear();
	}



	// ##############################################################


	/**
	 * Adds the user reward.
	 *
	 * @param userReward the user reward
	 */
	public void addUserReward(final UserReward userReward) {
//		if(userRewards.stream()
//				.filter(
//						r -> !r.attraction.attractionName
//						.equals(userReward.attraction)).count() == 0) {
//
//			userRewards.add(userReward);
//		}
		this.userRewards.add(userReward);
	}



	// ##############################################################



	/**
	 * Gets the last visited location.
	 *
	 * @return the last visited location
	 */
	public VisitedLocation getLastVisitedLocation() {
		return visitedLocations.get(visitedLocations.size() - 1);
	}



	// ##############################################################


}
