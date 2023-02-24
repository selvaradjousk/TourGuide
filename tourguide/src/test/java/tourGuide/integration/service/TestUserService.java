package tourGuide.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.UserNotFoundException;
import tourGuide.model.User;
import tourGuide.service.RewardsService;
import tourGuide.service.UserService;

@DisplayName("IT - Service - User")
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest("/integration-test.properties")
public class TestUserService {



    @Autowired
    UserService userService;

    @Autowired
    RewardsService rewardService;   
 


	// ##############################################################

    @DisplayName("Check <AddUser>"
    		+ " - Given an User with one visited location,"
    		+ " WHEN Requested AddUser,"
    		+ " then return User Added as expected")
    @Test
    public void testAddUser() {

    	// GIVEN
    	UUID userID = UUID.fromString("1851b7bd-737a-4c9d-9c2b-3b5829e417fa");
     	User user = new User(
     			userID,
     			"testUser",
     			"000",
     			"testUser@email.com");

    	// WHEN
     	userService.addUser(user);

    	// THEN
        assertThat(userService.getAllUsers()).contains(user);
    }


	// ##############################################################

    @DisplayName("Check <AddUser> - already exists <Exception>"
		+ "GIVEN an Username is already used "
		+ "WHEN Requested AddUser "
		+ "THEN throws DataAlreadyRegisteredException")	
    @Test
    public void testAddUserAlreadyExistsForExceptionThrown() {

    	// GIVEN
    	UUID userID = UUID.fromString("1851b7bd-737a-4c9d-9c2b-3b5829e417fa");
        User user = new User(userID, "internalUser1", "000", "existingUser@email.com");
 
        // THEN  <== WHEN
        assertThrows(DataAlreadyRegisteredException.class, ()
        		-> userService.addUser(user));
    }
    



	// ##############################################################

    @DisplayName("Check <getAllUsers>"
    		+ " - Given a userlist,"
    		+ " WHEN Requested GET all users,"
    		+ " then return All Users list")
    @Test
    public void testGetAllUsers() {

    	// WHEN
    	List<User> users = userService
    			.getAllUsers();

    	// THEN
        assertThat(users).isNotEmpty();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    

	// ##############################################################


    @DisplayName("Check <get User>"
    		+ " - Given a User existing,"
    		+ " WHEN Requested GET user,"
    		+ " then return user as expected")
    @Test
    public void testGetUser() {

    	// GIVEN // WHEN
    	User user = userService
    			.getUser("internalUser1");

    	// THEN
        assertNotNull(user);
        assertEquals("internalUser1", user.getUserName());

    }
    

	// ##############################################################


    @Test
    @DisplayName("Check <get User> - not exists <Exception>"
		+ "GIVEN an Username not exists "
		+ "WHEN Requested GET User "
		+ "THEN throws UserNotFoundException")	
    public void testGetUserNotExistingForUserNotFoundException() {
        
        // THEN  <== WHEN
        assertThrows(UserNotFoundException.class, ()
        		-> userService
        		.getUser("testUserDoesNotExist"));
        
    }

    

	// ##############################################################


    @DisplayName("Check <UpdateUserPreferences>"
    		+ " - Given an User's preference,"
    		+ " WHEN Requested UpdateUserPreferences,"
    		+ " then updates user preferences as expected")
    @Test
    public void testUpdateUserPreferences() {
    	

    	// GIVEN
        UserPreferencesDTO userPreferences = new UserPreferencesDTO(
       		 10,
       		 500,
       		 1000,
                5,
                5,
                2,
                3);

        // WHEN
        UserPreferencesDTO result = userService
        		.updateUserPreferences("internalUser1", userPreferences);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualToComparingFieldByField(userPreferences);
        
 
    }


	// ##############################################################


}
