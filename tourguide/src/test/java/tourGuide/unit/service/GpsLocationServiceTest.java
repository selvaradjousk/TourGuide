package tourGuide.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tourGuide.dto.AttractionDTO;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.proxy.MicroServiceTripDealsProxy;
import tourGuide.proxy.MicroserviceGpsProxy;
import tourGuide.proxy.MicroserviceRewardsProxy;
import tourGuide.service.GpsLocationService;
import tourGuide.service.RewardsService;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.LocationMapper;
import tourGuide.util.ProviderMapper;
import tourGuide.util.UserPreferencesMapper;
import tourGuide.util.UserRewardMapper;
import tourGuide.util.VisitedLocationMapper;

@DisplayName("Unit Test - Service - GpsLocation")
@ExtendWith(MockitoExtension.class)
public class GpsLocationServiceTest {

    @InjectMocks
    private GpsLocationService gpsLocationService;

    @Mock
    private RewardsService rewardsService;

    @Mock
    private MicroserviceGpsProxy gpsUtilMicroService;

    @Mock
    private MicroServiceTripDealsProxy tripDealsMicroservice;

    @Mock
    private MicroserviceRewardsProxy rewardsMicroService;

	@Mock
	private UserPreferencesMapper userPreferencesMapper;

	@Mock
	private UserRewardMapper userRewardMapper;

	@Mock
	private LocationMapper locationMapper;

	@Mock
	private VisitedLocationMapper visitedLocationMapper;

	@Mock
	private ProviderMapper providerMapper;

    @Mock
    private DistanceCalculator distanceCalculator;

    @Mock
    private InternalTestHelper internalTestHelper;

    private static UUID user1ID;

    private static UUID user2ID;

    private static User user1;

    private static User user2;


    private static Map<String, User> internalUser;


    private static VisitedLocation visitedLocation;

    private static VisitedLocationDTO visitedLocationDTO;





	// ##############################################################



    @BeforeEach
    public void setUp() {
        user1ID = UUID.randomUUID();
        user2ID = UUID.randomUUID();
        user1 = new User(user1ID, "testUser1", "000", "testUser1@email.com");
        user2 = new User(user2ID, "testUser2", "001", "testUser1@email.com");


        visitedLocation = new VisitedLocation(
        		user1ID,
        		new Location(33.817595, -117.922008),
        		new Date());

        visitedLocationDTO = new VisitedLocationDTO(
        		user1ID,
        		new Location(33.817595, -117.922008),
        		new Date());

        internalUser = new HashMap<>();
        internalUser.put("testUser1", user1);
        internalUser.put("testUser2", user2);



    }



	// ##############################################################


    @DisplayName("Check <GetUserLocation>"
    		+ " - Given a User with one visited location,"
    		+ " when GET USER location,"
    		+ " then return USER expected location")
    @Test
	public void testGetUserLocation() {

    	// GIVEN
    	LocationDTO expectedLocation = new LocationDTO(-160.326003, -73.869629);

    	when(internalTestHelper
    			.getInternalUserMap())
    	.thenReturn(internalUser);

    	when(gpsUtilMicroService
    			.getUserLocation(any(UUID.class)))
    	.thenReturn(visitedLocationDTO);

    	when(locationMapper
    			.toLocationDTO(any(Location.class)))
    	.thenReturn(expectedLocation);

        // WHEN
        LocationDTO result = gpsLocationService
        		.getUserLocation(user1.getUserName());

        // THEN
        assertThat(result).isEqualToComparingFieldByField(expectedLocation);
    }




	// ##############################################################


    @DisplayName("Check <GetUserLocation>"
    		+ " - Given a User with one visited location,"
    		+ " when GET USER location,"
    		+ " then return USER expected location")
    @Test
    public void testUserLocationWithVisitedLocation() {

    	// GIVEN
    	user1.addToVisitedLocations(visitedLocation);
        LocationDTO expectedLocation = new LocationDTO(33.817595, -117.922008);

        when(internalTestHelper
        		.getInternalUserMap())
        .thenReturn(internalUser);

        when(locationMapper
        		.toLocationDTO(any(Location.class)))
        .thenReturn(expectedLocation);

        // WHEN
        LocationDTO result = gpsLocationService
        		.getUserLocation(user1.getUserName());

        // THEN
        assertThat(result).isEqualToComparingFieldByField(expectedLocation);
        assertEquals(1, user1.getVisitedLocations().size());
    }




	// ##############################################################

    
    
    @DisplayName("Check <get All Users Locations>"
    		+ " - Given a list of users,"
    		+ " WHEN Requested GET all users locations,"
    		+ " then return users locations as expected")
	@Test
	public void testGetAllUsersCurrentLocations() {

		// GIVEN
        user1.addToVisitedLocations(visitedLocation);
        user2.addToVisitedLocations(new VisitedLocation(
        		user2ID,
        		new Location(35.985512, -92.757652),
        		new Date()));

        LocationDTO user1Location = new LocationDTO(
        		33.817595, -117.922008);
        LocationDTO user2Location = new LocationDTO(
        		35.985512, -92.757652);
        
        when(internalTestHelper
        		.getInternalUserMap())
        .thenReturn(internalUser);
        
        when(internalTestHelper
        		.getInternalUserMap())
        .thenReturn(internalUser);
        

        lenient().when(locationMapper
        		.toLocationDTO(user1.getLastVisitedLocation()
        				.getLocation())).thenReturn(user1Location);

        lenient().when(locationMapper
        		.toLocationDTO(
        				user2.getLastVisitedLocation().getLocation()))
        .thenReturn(user2Location);

        // WHEN
        Map<String, LocationDTO> result = gpsLocationService
        		.getAllUserRecentLocation();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.values()).contains(user1Location, user2Location);

	}



	// ##############################################################
	

	

	
    @DisplayName("Check <get track User>"
    		+ " - Given an User,"
    		+ " WHEN Requested track User Location,"
    		+ " then return location and adds to history as expected")
    @Test
    public void testTrackUser() {

    	// GIVEN
        when(gpsUtilMicroService
        		.getUserLocation(any(UUID.class)))
        .thenReturn(visitedLocationDTO);
        
        when(visitedLocationMapper
        		.toVisitedLocation(any(VisitedLocationDTO.class)))
        .thenReturn(visitedLocation);

        // WHEN
        gpsLocationService.trackUserLocation(user1).join();

        // THEN
        assertEquals((visitedLocation), user1.getVisitedLocations().get(0));
    }
    


	// ##############################################################
	

	

    @DisplayName("Check <get User Attraction Recommendation>"
    		+ " - Given an User,"
    		+ " WHEN Requested getUserAttractionRecommendation,"
    		+ " then return 5 nearby Attractions as expected")
    @Test
    public void testGetUserAttractionRecommendation() {

    	// GIVEN
    	user1.addToVisitedLocations(visitedLocation);

    	Location userLocation = user1.getLastVisitedLocation().getLocation();
        LocationDTO userLocationDTO = new LocationDTO(-160.326003, -73.869629);

        UUID userID = user1.getUserId();

        AttractionDTO attraction1 = new AttractionDTO(
        		UUID.randomUUID(), "Disneyland", "Anaheim", "CA", new Location(33.817595D, -117.922008D));
        AttractionDTO attraction2 = new AttractionDTO(
        		UUID.randomUUID(), "Jackson Hole", "Jackson Hole", "WY", new Location(43.582767D, -110.821999D));
        AttractionDTO attraction3 = new AttractionDTO(
        		UUID.randomUUID(), "Mojave","Kelso", "CA", new Location(35.141689D, -115.510399D));
        AttractionDTO attraction4 = new AttractionDTO(
        		UUID.randomUUID(), "Kartchner Caverns", "Benson", "AZ", new Location(31.837551D, -110.347382D));
        AttractionDTO attraction5 = new AttractionDTO(
        		UUID.randomUUID(),"Joshua Tree", "Joshua Tree", "CA", new Location(33.881866D, -115.90065D));
        AttractionDTO attraction6 = new AttractionDTO(
        		UUID.randomUUID(),"Buffalo", "St Joe", "AR", new Location(35.985512D, -92.757652D));
        AttractionDTO attraction7 = new AttractionDTO(
        		UUID.randomUUID(),"Hot Springs", "Hot Springs", "AR", new Location(34.52153D, -93.042267D));
        AttractionDTO attraction8 = new AttractionDTO(
        		UUID.randomUUID(),"Kartchner Caverns State Park", "Benson", "AZ", new Location(31.837551D, -110.347382D));
        AttractionDTO attraction9 = new AttractionDTO(
        		UUID.randomUUID(),"Legend Valley", "Thornville", "OH", new Location(39.937778D, -82.40667D));
        AttractionDTO attraction10 = new AttractionDTO(
        		UUID.randomUUID(),"Flowers Bakery of London", "Flowers Bakery of London", "KY", new Location(37.131527D, -84.07486D));

        List<AttractionDTO> attractions = Arrays.asList(
        		attraction1,
        		attraction2,
        		attraction3,
        		attraction4,
        		attraction5,
        		attraction6,
        		attraction7,
        		attraction8,
        		attraction9,
        		attraction10 );

        when(internalTestHelper
        		.getInternalUserMap())
        .thenReturn(internalUser);
        
        lenient().when(locationMapper
        		.toLocationDTO(any(Location.class)))
        .thenReturn(userLocationDTO);
        
        lenient().when(locationMapper
        		.toLocation(any(LocationDTO.class)))
        .thenReturn(userLocation);
        
        lenient().when(gpsUtilMicroService
        		.getAttractions())
        .thenReturn(attractions);
        
        lenient().when(distanceCalculator.getDistanceInMiles(attraction1.getLocation(), userLocation)).thenReturn(100.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction2.getLocation(), userLocation)).thenReturn(200.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction3.getLocation(), userLocation)).thenReturn(300.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction4.getLocation(), userLocation)).thenReturn(400.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction5.getLocation(), userLocation)).thenReturn(500.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction6.getLocation(), userLocation)).thenReturn(600.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(700.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(800.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(900.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(900.00);

        lenient().when(rewardsMicroService.getRewardPoints(attraction1.getAttractionId(), userID)).thenReturn(100);
        lenient().when(rewardsMicroService.getRewardPoints(attraction2.getAttractionId(), userID)).thenReturn(200);
        lenient().when(rewardsMicroService.getRewardPoints(attraction3.getAttractionId(), userID)).thenReturn(300);
        lenient().when(rewardsMicroService.getRewardPoints(attraction4.getAttractionId(), userID)).thenReturn(400);
        lenient().when(rewardsMicroService.getRewardPoints(attraction5.getAttractionId(), userID)).thenReturn(500);
    	
    	// WHEN 
    	UserAttractionRecommendationDTO result = gpsLocationService
    			.getUserAttractionRecommendation(user1.getUserName());

    	// THEN
        assertThat(result.getNearbyAttractions()).isNotEmpty();
        assertEquals(5, result.getNearbyAttractions().size());
//        assertEquals("Kartchner Caverns State Park", result.getNearbyAttractions().get(0).getAttractionName());
//        assertEquals("Flowers Bakery of London", result.getNearbyAttractions().get(1).getAttractionName());
//        assertEquals("Legend Valley", result.getNearbyAttractions().get(2).getAttractionName());
//        assertEquals("Disneyland", result.getNearbyAttractions().get(3).getAttractionName());
//        assertEquals("Jackson Hole", result.getNearbyAttractions().get(4).getAttractionName());
        
    }




	// ##############################################################

      


    @DisplayName("Check <getTotalRewardPointsForUser>"
    		+ " - Given an Username,"
    		+ " WHEN Requested getTotalRewardPointsForUser,"
    		+ " then return user reward points as expected")
    @Test
    public void getTotalRewardPointsForUser() {


    	// GIVEN
    	user1.addToVisitedLocations(visitedLocation);

    	Location userLocation = user1.getLastVisitedLocation().getLocation();
        LocationDTO userLocationDTO = new LocationDTO(-160.326003, -73.869629);

        UUID userID = user1.getUserId();

        AttractionDTO attraction1 = new AttractionDTO(
        		UUID.randomUUID(), "Disneyland", "Anaheim", "CA", new Location(33.817595D, -117.922008D));
        AttractionDTO attraction2 = new AttractionDTO(
        		UUID.randomUUID(), "Jackson Hole", "Jackson Hole", "WY", new Location(43.582767D, -110.821999D));
        AttractionDTO attraction3 = new AttractionDTO(
        		UUID.randomUUID(), "Mojave","Kelso", "CA", new Location(35.141689D, -115.510399D));
        AttractionDTO attraction4 = new AttractionDTO(
        		UUID.randomUUID(), "Kartchner Caverns", "Benson", "AZ", new Location(31.837551D, -110.347382D));
        AttractionDTO attraction5 = new AttractionDTO(
        		UUID.randomUUID(),"Joshua Tree", "Joshua Tree", "CA", new Location(33.881866D, -115.90065D));
        AttractionDTO attraction6 = new AttractionDTO(
        		UUID.randomUUID(),"Buffalo", "St Joe", "AR", new Location(35.985512D, -92.757652D));
        AttractionDTO attraction7 = new AttractionDTO(
        		UUID.randomUUID(),"Hot Springs", "Hot Springs", "AR", new Location(34.52153D, -93.042267D));
        AttractionDTO attraction8 = new AttractionDTO(
        		UUID.randomUUID(),"Kartchner Caverns State Park", "Benson", "AZ", new Location(31.837551D, -110.347382D));
        AttractionDTO attraction9 = new AttractionDTO(
        		UUID.randomUUID(),"Legend Valley", "Thornville", "OH", new Location(39.937778D, -82.40667D));
        AttractionDTO attraction10 = new AttractionDTO(
        		UUID.randomUUID(),"Flowers Bakery of London", "Flowers Bakery of London", "KY", new Location(37.131527D, -84.07486D));

        List<AttractionDTO> attractions = Arrays.asList(
        		attraction1,
        		attraction2,
        		attraction3,
        		attraction4,
        		attraction5,
        		attraction6,
        		attraction7,
        		attraction8,
        		attraction9,
        		attraction10 );

        when(internalTestHelper
        		.getInternalUserMap())
        .thenReturn(internalUser);
        
        lenient().when(locationMapper
        		.toLocationDTO(any(Location.class)))
        .thenReturn(userLocationDTO);
        
        lenient().when(locationMapper
        		.toLocation(any(LocationDTO.class)))
        .thenReturn(userLocation);
        
        lenient().when(gpsUtilMicroService
        		.getAttractions())
        .thenReturn(attractions);
        
        lenient().when(distanceCalculator.getDistanceInMiles(attraction1.getLocation(), userLocation)).thenReturn(100.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction2.getLocation(), userLocation)).thenReturn(200.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction3.getLocation(), userLocation)).thenReturn(300.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction4.getLocation(), userLocation)).thenReturn(400.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction5.getLocation(), userLocation)).thenReturn(500.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction6.getLocation(), userLocation)).thenReturn(600.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(700.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(800.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(900.00);
        lenient().when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(), userLocation)).thenReturn(900.00);

        lenient().when(rewardsMicroService.getRewardPoints(attraction1.getAttractionId(), userID)).thenReturn(100);
        lenient().when(rewardsMicroService.getRewardPoints(attraction2.getAttractionId(), userID)).thenReturn(200);
        lenient().when(rewardsMicroService.getRewardPoints(attraction3.getAttractionId(), userID)).thenReturn(300);
        lenient().when(rewardsMicroService.getRewardPoints(attraction4.getAttractionId(), userID)).thenReturn(400);
        lenient().when(rewardsMicroService.getRewardPoints(attraction5.getAttractionId(), userID)).thenReturn(500);
    	
    	// WHEN 
//    	UserAttractionRecommendationDTO result = tourGuideService
//    			.getUserAttractionRecommendation(user1.getUserName());

    	
        
        // WHEN
        int result = gpsLocationService
        		.getTotalRewardPointsForUser(user1.getUserName());

        // THEN
        assertNotNull(result);
        assertThat(result > 500);
        assertEquals(300, result);
    }



	// ##############################################################



}