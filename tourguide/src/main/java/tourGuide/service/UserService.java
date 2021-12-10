package tourGuide.service;

import java.util.List;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.UserNotFoundException;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.util.UserPreferencesMapper;

@Service
public class UserService implements IUserService {




	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(UserService.class);


    /** The user preferences mapper. */
    private final UserPreferencesMapper userPreferencesMapper;

	
	/** The internal test helper. */
	private InternalTestHelper internalTestHelper
						= new InternalTestHelper();


	// ##############################################################




	@Autowired
	public UserService(
			UserPreferencesMapper userPreferencesMapper,
			InternalTestHelper internalTestHelper) {

		this.userPreferencesMapper = userPreferencesMapper;
		this.internalTestHelper = internalTestHelper;
	}




	// ##############################################################




	/**
	 * Gets the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	@Override
	public User getUser(String userName) {

//		logger.info("## getUser() for user {} invoked ", userName );

		User user = internalTestHelper.getInternalUserMap().get(userName);

        if (user == null) {
            throw new UserNotFoundException("Username not found");
        }

		return user;
	}



	// ##############################################################



	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	@Override
	public List<User> getAllUsers() {

		logger.info("## getAllUser() method invoked");

		List<User> users = internalTestHelper
				.getInternalUserMap()
				.values()
				.stream()
				.collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new UserNotFoundException("User List unavailable");
        }

        return users;		
		
	}


	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	@Override
	public void addUser(final User user) {

		logger.info("## addUser() for user {} invoked ", user );


		if(!internalTestHelper
				.internalUserMap
				.containsKey(user.getUserName())) {

			logger.info("## addUser() for"
					+ " user {} gets added"
					+ " to map ", user.getUserName() );

			internalTestHelper
			.internalUserMap.put(
					user.getUserName(),
					user);
		} else {
			throw new DataAlreadyRegisteredException(""
					+ "Username exists already");
		}
	}



	// ##############################################################



 
	/**
	 * Update user preferences.
	 *
	 * @param userName the user name
	 * @param userPreferencesDTO the user preferences DTO
	 * @return the user preferences DTO
	 */
	@Override
	public UserPreferencesDTO updateUserPreferences(
    		final String userName,
    		final UserPreferencesDTO userPreferencesDTO) {



        User user = getUser(userName);


        UserPreferences userPreferences = user
        		.getUserPreferences();
        userPreferences.setAttractionProximity(userPreferencesDTO
        		.getAttractionProximity());
        userPreferences.setHighPricePoint(Money.of(userPreferencesDTO
        		.getHighPricePoint(), userPreferences
        		.getCurrency()));
        userPreferences.setLowerPricePoint(Money.of(userPreferencesDTO
        		.getLowerPricePoint(), userPreferences
        		.getCurrency()));
        userPreferences.setTripDuration(userPreferencesDTO
        		.getTripDuration());
        userPreferences.setTicketQuantity(userPreferencesDTO
        		.getTicketQuantity());
        userPreferences.setNumberOfAdults(userPreferencesDTO
        		.getNumberOfAdults());
        userPreferences.setNumberOfChildren(userPreferencesDTO
        		.getNumberOfChildren());

        
        UserPreferencesDTO userPreferencesUpdated = userPreferencesMapper
        		.toUserPreferencesDTO(user.getUserPreferences());

        return userPreferencesUpdated;

    }



	// ##############################################################









}
