package tourGuide.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tourGuide.dto.AttractionDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxy.MicroserviceRewardsProxy;
import tourGuide.service.GpsUtilMicroService;
import tourGuide.service.RewardsService;
import tourGuide.util.AttractionMapper;
import tourGuide.util.DistanceCalculator;

@DisplayName("Unit Test - Service - Rewards")
@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {


    @InjectMocks
    private RewardsService rewardsService;

    @Mock
    private GpsUtilMicroService gpsUtilMicroService;


    @Mock
    private MicroserviceRewardsProxy rewardsMicroService;

	@Mock
    private AttractionMapper attractionMapper;

    @Mock
    private DistanceCalculator distanceCalculator;

    @Mock
    private InternalTestHelper internalTestHelper;


    private static VisitedLocation visitedLocation;
    
    private static Location location, attractionLocation, attractionLocationUserWithNoVisitedAttraction;

    private static Attraction attraction;

    private static AttractionDTO attractionDTO;

    User user = new User();
    User userWithNoVisitedAttraction = new User();
    User userWithNoVisitedLocation = new User();
    
    private static List<AttractionDTO> attractions;
    
	// ##############################################################



    @BeforeEach
    public void setUp() {
    	

    	user = new User();
    	userWithNoVisitedAttraction = new User();
    	userWithNoVisitedLocation = new User();
    	
        user.setUserId(UUID.randomUUID());

        location = new Location(33.817595, -117.922008);

        visitedLocation = new VisitedLocation(
        		UUID.randomUUID(),
        		location, new Date());

        user.addToVisitedLocations(visitedLocation);

        userWithNoVisitedAttraction.addToVisitedLocations(visitedLocation);


        attractionLocation = new Location(34.817595, -117.922008);

        attractionLocationUserWithNoVisitedAttraction = new Location(
        		-34.817595, -117.922008);

    }

	// ##############################################################



    @DisplayName("Check <testUserGetRewards>"
    		+ " - Given a User with specifically one visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return one rewards as expected")	
	@Test
	public void testUserGetRewards() throws InterruptedException {

    	// GIVEN
        attractionDTO = new AttractionDTO(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);

        attraction = new Attraction(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);

        attractions = Arrays.asList(attractionDTO);
        
    	lenient().when(gpsUtilMicroService
    			.getAttractions())
    	.thenReturn(attractions);
    	
        when(distanceCalculator
        		.getDistanceInMiles(location, location))
        .thenReturn(8.00);
        
        lenient().when(distanceCalculator
        		.getDistanceInMiles(location, location))
        .thenReturn(8.00);
        
        when(attractionMapper
        		.toAttraction(attractionDTO))
        .thenReturn(attraction);

        // WHEN
		 rewardsService.calculateRewards(user);

		 // THEN
		assertNotNull(user.getUserRewards());
		assertEquals(1, user.getUserRewards().size());
	}



	// ##############################################################



    @DisplayName("Check <testUserGetRewards> No visited attraction"
    		+ " - Given a User with specifically No visited attraction,"
    		+ " when Calculate rewards,"
    		+ " then return No rewards as expected")	
	@Test
	public void testUserGetRewardsWithNoVisitedAttraction() throws InterruptedException {

    	// GIVEN
        attractionDTO = new AttractionDTO(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocationUserWithNoVisitedAttraction);
        
    	attractions = Arrays.asList(attractionDTO);

    	lenient().when(gpsUtilMicroService
    			.getAttractions())
    	.thenReturn(attractions);
    	
    	lenient().when(distanceCalculator
    			.getDistanceInMiles(attractionLocationUserWithNoVisitedAttraction, location))
    	.thenReturn(800.00);

    	
    	
        // WHEN
		 rewardsService.calculateRewards(userWithNoVisitedAttraction);

		 
		 
		 // THEN
		assertEquals(0, userWithNoVisitedAttraction.getUserRewards().size());

		InOrder inOrder = inOrder(gpsUtilMicroService, distanceCalculator);
        inOrder.verify(gpsUtilMicroService).getAttractions();
//        inOrder.verify(distanceCalculator).getDistanceInMiles(attractionLocation, location);
        
        verify(gpsUtilMicroService, times(1)).getAttractions();
        verify(distanceCalculator, times(0)).getDistanceInMiles(attractionLocation, location);
	}


	// ##############################################################




    @DisplayName("Check <testUserGetRewards> No visited location"
    		+ " - Given a User with specifically No visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return No rewards as expected")	
	@Test
	public void testUserGetRewardsWithNoVisitedLocation() throws InterruptedException {

    	// GIVEN => No visited location
    	
    	// WHEN
		 rewardsService.calculateRewards(userWithNoVisitedLocation);

		 // THEN
		assertEquals(0, userWithNoVisitedAttraction.getUserRewards().size());

	}



	// ##############################################################




    @DisplayName("Check <testUserGetRewards> on revisiting Same location"
    		+ " - Given a User with revisiting one same visited locaiton,"
    		+ " when Calculate rewards,"
    		+ " then return one rewards as expected")	
	@Test
	public void testUserGetRewardsOnRevisitingSameLocation() throws InterruptedException {

    	// GIVEN
        attractionDTO = new AttractionDTO(UUID.randomUUID(), "attraction name",
                "attraction city", "attraction state", location);
        attraction = new Attraction(UUID.randomUUID(), "attraction name",
                "attraction city", "attraction state", location);
        attractions = Arrays.asList(attractionDTO);
        
        user.addUserReward(new UserReward(visitedLocation, attraction, 500));
        
    	lenient().when(gpsUtilMicroService
    			.getAttractions())
    	.thenReturn(attractions);
    	
        when(distanceCalculator
        		.getDistanceInMiles(location, location))
        .thenReturn(8.00);
        
        when(distanceCalculator
        		.getDistanceInMiles(location, location))
        .thenReturn(8.00);
		

        
        // WHEN
		 rewardsService.calculateRewards(user);

		 
		 
		 // THEN
		assertEquals(1, user.getUserRewards().size());
		
 	}



	// ##############################################################







    @DisplayName("Check <isWithinAttractionProximity> "
    		+ " - Given a User with specifically one visited locaiton,"
    		+ " when Calculate request isWithinAttractionProximity,"
    		+ " then return result as expected")		
	@Test
	public void isWithinAttractionProximity() {

    	// GIVEN

        attraction = new Attraction(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);


		VisitedLocation visitedLocation = new VisitedLocation(
				user.getUserId(),
				attraction.getLocation(),
				new Date());
	

		// WHEN
		Boolean result = rewardsService
				.isWithinAttractionProximity(
						visitedLocation,
						attraction.getLocation());
		
		
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
        VisitedLocation visitedLocation = new VisitedLocation(
        		UUID.randomUUID(),
        		location,
        		new Date());
        
        user.addToVisitedLocations(visitedLocation);

        AttractionDTO attractionDTO = new AttractionDTO(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);
        
        Attraction attraction = new Attraction(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);

        List<AttractionDTO> attractions = Arrays.asList(attractionDTO);

        when(gpsUtilMicroService
        		.getAttractions())
        .thenReturn(attractions);
        
        when(distanceCalculator
        		.getDistanceInMiles(any(Location.class), any(Location.class)))
        .thenReturn(10.00);
        
        when(rewardsMicroService
        		.getRewardPoints(any(UUID.class), any(UUID.class)))
        .thenReturn(1000);
        
        when(attractionMapper
        		.toAttraction(attractionDTO))
        .thenReturn(attraction);

        rewardsService.calculateRewardAsync(user).join();

        assertEquals(1, user.getUserRewards().size());
    }    
    

	// ##############################################################

    


    @DisplayName("Check <getRewardPoints> "
    		+ " - Given an User ,"
    		+ " when Calculate request getRewardPoints,"
    		+ " then return rewardPoints as expected")	
    @Test
    public void testgetRewarPoints() {

		
    	// GIVEN
	
        VisitedLocation visitedLocation = new VisitedLocation(
        		UUID.randomUUID(),
        		location,
        		new Date());
        
        user.addToVisitedLocations(visitedLocation);

        AttractionDTO attractionDTO = new AttractionDTO(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);
        
        Attraction attraction = new Attraction(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);
		

        user.addToVisitedLocations(
        		new VisitedLocation(
        				user.getUserId(),
        				attraction.getLocation(),
        				new Date()));


        
        when(rewardsMicroService
        		.getRewardPoints(any(UUID.class), any(UUID.class)))
        .thenReturn(1000);
        
        when(attractionMapper
        		.toAttraction(attractionDTO))
        .thenReturn(attraction);
        
        // WHEN
        UserReward result = rewardsService
        		.getRewardPoints(user, user.getLastVisitedLocation(), attractionDTO);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getVisitedLocation());
        assertNotNull(result.getRewardPoints());
        assertNotNull(result.getAttraction());
        
        assertTrue(result.getRewardPoints() > 1);
        assertEquals(1000, result.getRewardPoints());
    }     

	// ##############################################################


    @DisplayName("Check <CheckIfUserIsOfferedRewardToThisAttractionSpot> "
    		+ " - Given an User with specific attraction spot,"
    		+ " when Calculate request if user has rewards for the spot,"
    		+ " then return result as expected")	
    @Test
    public void testCheckIfUserIsOfferedRewardToThisAttractionSpot() {


       	// GIVEN
    	
        VisitedLocation visitedLocation = new VisitedLocation(
        		UUID.randomUUID(),
        		location,
        		new Date());
        
        user.addToVisitedLocations(visitedLocation);

        AttractionDTO attractionDTO = new AttractionDTO(
        		UUID.randomUUID(),
        		"attraction name",
                "attraction city",
                "attraction state",
                attractionLocation);
        

        // WHEN
        Boolean result = rewardsService
        		.checkIfUserIsOfferedRewardToThisAttractionSpot(
        				user,
        				attractionDTO);

        // THEN
        assertNotNull(result);
        assertTrue(result);

    }     



	// ##############################################################

    
    
      

}