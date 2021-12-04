package tourGuide.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tourGuide.dto.LocationDTO;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.GpsUtilMicroService;
import tourGuide.service.RewardsMicroService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.service.TripDealsMicroService;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.LocationMapper;
import tourGuide.util.ProviderMapper;
import tourGuide.util.UserPreferencesMapper;
import tourGuide.util.UserRewardMapper;
import tourGuide.util.VisitedLocationMapper;

@DisplayName("Unit Test - Service - TourGuide")
@ExtendWith(MockitoExtension.class)
public class TourGuideServiceTest {

    @InjectMocks
    private TourGuideService tourGuideService;

    @Mock
    private RewardsService rewardsService;

    @Mock
    private GpsUtilMicroService GpsUtilMicroService;

    @Mock
    private TripDealsMicroService tripDealsMicroservice;

    @Mock
    private RewardsMicroService rewardsMicroService;


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
        		new Location(-160.326003, -73.869629),
        		new Date());

        visitedLocationDTO = new VisitedLocationDTO(
        		user1ID,
        		new Location(-160.326003, -73.869629),
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

    	when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);

    	when(GpsUtilMicroService
    			.getUserLocation(any(UUID.class)))
    	.thenReturn(visitedLocationDTO);

    	when(locationMapper
    			.toLocationDTO(any(Location.class)))
    	.thenReturn(expectedLocation);

        // WHEN
        LocationDTO result = tourGuideService
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
        LocationDTO result = tourGuideService
        		.getUserLocation(user1.getUserName());

        // THEN
        assertThat(result).isEqualToComparingFieldByField(expectedLocation);
        assertThat(user1.getVisitedLocations().size()).isEqualTo(1);
    }




	// ##############################################################

 

}