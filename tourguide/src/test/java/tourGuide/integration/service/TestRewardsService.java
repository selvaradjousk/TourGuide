package tourGuide.integration.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxy.MicroserviceGpsProxy;
import tourGuide.service.RewardsService;
import tourGuide.service.UserService;
import tourGuide.util.AttractionMapper;

@DisplayName("IT Test - Service - Rewards")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/integration-test.properties")
public class TestRewardsService {


    @Autowired
    private RewardsService rewardsService;


    @Autowired
    private MicroserviceGpsProxy gpsUtilMicroService;

    @Autowired
    private UserService userService;

    
    @Autowired
    private AttractionMapper attractionMapper;

	// ##############################################################



    @DisplayName("Check <testUserGetRewards>"
    		+ " - Given a User with specifically one visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return one rewards as expected")	
	@Test
	public void testUserGetRewards() throws InterruptedException {

    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		AttractionDTO attraction = gpsUtilMicroService
				.getAttractions().get(0);

		user.addToVisitedLocations(new VisitedLocation(
				user.getUserId(),
				attraction.getLocation(),
				new Date()));


		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 // THEN
		assertEquals(1, user.getUserRewards().size());
	}



	// ##############################################################



    @DisplayName("Check <testUserGetRewards> No visited location"
    		+ " - Given a User with specifically No visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return No rewards as expected")	
	@Test
	public void testUserGetRewardsWithNoVisitedAttraction() throws InterruptedException {

    	
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 // THEN
		assertEquals(0, user.getUserRewards().size());
	}



	// ##############################################################




    @DisplayName("Check <testUserGetRewards> on revisiting Same location"
    		+ " - Given a User with revisiting one same visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return one rewards as expected")	
	@Test
	public void testUserGetRewardsOnRevisitingSameLocation() throws InterruptedException {

    	
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		AttractionDTO attraction = gpsUtilMicroService
				.getAttractions().get(0);

		VisitedLocation visitedLocation1 = new VisitedLocation(
												user.getUserId(),
												attraction.getLocation(),
												new Date());


		VisitedLocation visitedLocation2 = new VisitedLocation(
												user.getUserId(),
												attraction.getLocation(),
												new Date());

        user.addToVisitedLocations(visitedLocation1);
        user.addToVisitedLocations(visitedLocation2);
        user.addUserReward(new UserReward(
        		visitedLocation1, attractionMapper
        		.toAttraction(attraction), 500));
		
		assertEquals(1, user.getUserRewards().size());
		
		
		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 
		 // THEN
		assertEquals(1, user.getUserRewards().size());
	}



	// ##############################################################




    @DisplayName("Check <testUserGetRewards> For No Attractions Near VisitedLocation"
    		+ " - Given a User with specifically one visited locaiton with no attraction nearby,"
    		+ " when Calculate rewards,"
    		+ " then return no rewards as expected")	
	@Test
	public void testUserGetRewardsForNoAttractionsNearVisitedLocation() throws InterruptedException {

    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		gpsUtilMicroService
				.getAttractions()
				.get(0)
				.setLocation(new Location(30.881866, -115.90065));

        VisitedLocation visitedLocation = new VisitedLocation(
        		user.getUserId(),
        		new Location(-30.881866, -115.90065),
        		new Date());
		
        user.addToVisitedLocations(visitedLocation);

		assertEquals(0, user.getUserRewards().size());

		
		// WHEN
		 rewardsService.calculateRewards(user);

		 
		 // THEN
		assertEquals(0, user.getUserRewards().size());
	}



	// ##############################################################





    @DisplayName("Check <isWithinAttractionProximity> "
    		+ " - Given a User with specifically one visited locaiton,"
    		+ " when Calculate request isWithinAttractionProximity,"
    		+ " then return result as expected")		
	@Test
	public void isWithinAttractionProximity() {

		
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");

		AttractionDTO attraction = gpsUtilMicroService
				.getAttractions().get(0);
		
		VisitedLocation visitedLocation = new VisitedLocation(
				user.getUserId(),
				attraction.getLocation(),
				new Date());
		

		// WHEN
		Boolean result = rewardsService
				.isWithinAttractionProximity(
						visitedLocation,
						visitedLocation.getLocation());
		
		
		// THEN <== // WHEN
		assertNotNull(result);
		assertTrue(result);
	}



	// ##############################################################




    @DisplayName("Check <calculateRewardAsync> "
    		+ " - Given an User ,"
    		+ " when Calculate request calculateRewardAsync,"
    		+ " then return one result as expected")	
    @Test
    public void testCalculateRewardAsyncForOneVisitedLocation() {

		
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");
		
        AttractionDTO attraction1 = gpsUtilMicroService
        		.getAttractions().get(0);
        
        user.addToVisitedLocations(
        		new VisitedLocation(
        				user.getUserId(),
        				attraction1.getLocation(),
        				new Date()));

        // WHEN
        rewardsService.calculateRewardAsync(user).join();

        // THEN
        assertEquals(1, user.getUserRewards().size());
    }     



	// ##############################################################

    


    @DisplayName("Check <getRewardPoints> "
    		+ " - Given an User ,"
    		+ " when Calculate request getRewardPoints,"
    		+ " then return rewardPoints as expected")	
    @Test
    public void testGetRewarPoints() {

		
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");
		
		
		
        AttractionDTO attraction = gpsUtilMicroService
        		.getAttractions().get(0);
        
        user.addToVisitedLocations(
        		new VisitedLocation(
        				user.getUserId(),
        				attraction.getLocation(),
        				new Date()));

        // WHEN
        UserReward result = rewardsService.getRewardPoints(user, user.getLastVisitedLocation(), attraction);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getVisitedLocation());
        assertNotNull(result.getRewardPoints());
        assertNotNull(result.getAttraction());
        
        assertTrue(result.getRewardPoints() > 1);
    }     



	// ##############################################################


    


    @DisplayName("Check <CheckIfUserIsOfferedRewardToThisAttractionSpot> "
    		+ " - Given an User with specific attraction spot,"
    		+ " when Calculate request if user has rewards for the spot,"
    		+ " then return result as expected")	
    @Test
    public void testCheckIfUserIsOfferedRewardToThisAttractionSpot() {

		
    	// GIVEN
		User user = new User(
				UUID.randomUUID(),
				"jon",
				"000",
				"jon@tourGuide.com");
		
		
		
        AttractionDTO attraction = gpsUtilMicroService
        		.getAttractions().get(0);
        
        user.addToVisitedLocations(
        		new VisitedLocation(
        				user.getUserId(),
        				attraction.getLocation(),
        				new Date()));

        // WHEN
        Boolean result = rewardsService
        		.checkIfUserIsOfferedRewardToThisAttractionSpot(
        				user,
        				attraction);

        // THEN
        assertNotNull(result);
        assertTrue(result);

    }     



	// ##############################################################



    @DisplayName("Check <testGetUserRewards>"
    		+ " - Given an User with reward,"
    		+ " WHEN Requested testGetUserRewards,"
    		+ " then return user rewards as expected")
    @Test
    public void testGetUserRewards() {

    	// GIVEN
    	User user = userService.getUser("internalUser1");
        user.getUserRewards().clear();
        VisitedLocation visitedLocation = new VisitedLocation(
        		user.getUserId(),
        		new Location(30.881866, -115.90065),
        		new Date());
        
        Attraction attraction = new Attraction(
        		UUID.randomUUID(),
        		"Disneyland" ,
        		"Anaheim" ,
                "CA",
                new Location(33.881866, -115.90065));
        
        UserReward userReward = new UserReward(
        		visitedLocation,
        		attraction,
        		1000);
        
        user.addUserReward(userReward);

        // WHEN
        List<UserRewardDTO> result = rewardsService
        		.getUserRewards("internalUser1");

        // THEN
        assertNotNull(result);
        assertEquals(1000, result.get(0).getRewardPoints());

    }



	// ##############################################################



    @DisplayName("Check <testGetUserRewards> no rewards"
    		+ " - Given an User with no reward,"
    		+ " WHEN Requested testGetUserRewards,"
    		+ " then return user rewards (empty) as expected")
    @Test
    public void testGetUserRewardsWhenNoRewards() {

    	// GIVEN
    	User user = userService.getUser("internalUser2");
        user.getUserRewards().clear();
        
        // WHEN
        List<UserRewardDTO> result = rewardsService
        		.getUserRewards("internalUser2");

        // THEN
        assertNotNull(result);
        assertThat(result).isEmpty();
    }



	// ##############################################################

		
}
