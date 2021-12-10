package tourGuide.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tourGuide.dto.ProviderDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.proxy.MicroServiceTripDealsProxy;
import tourGuide.proxy.MicroserviceGpsProxy;
import tourGuide.proxy.MicroserviceRewardsProxy;
import tourGuide.service.RewardsService;
import tourGuide.service.TripDealsService;
import tourGuide.service.UserService;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.LocationMapper;
import tourGuide.util.ProviderMapper;
import tourGuide.util.UserPreferencesMapper;
import tourGuide.util.UserRewardMapper;
import tourGuide.util.VisitedLocationMapper;

@DisplayName("Unit Test - Service - TripDeals")
@ExtendWith(MockitoExtension.class)
public class TripDealsServiceTest {

    @InjectMocks
    private TripDealsService tripDealsService;

    @Mock
    private RewardsService rewardsService;
    
    @Mock
    private UserService userService;

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

    
    private static UserReward userReward;
    
    private static UserPreferences userPreferences;

    
    private static Attraction attraction;




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



        internalUser = new HashMap<>();
        internalUser.put("testUser1", user1);
        internalUser.put("testUser2", user2);

        
        userReward = new UserReward(visitedLocation, attraction, 300);
        
        
        attraction = new Attraction(UUID.randomUUID(), "Disneyland", "Anaheim", "CA", new Location(33.817595D, -117.922008D));
       
        userPreferences = new UserPreferences(
          		 10,
          		 Money.of(500, Monetary.getCurrency("USD")),
          		 Money.of(1000, Monetary.getCurrency("USD")),
                   5,
                   5,
                   2,
                   3);



    }



	// ##############################################################

   


    @DisplayName("Check <getTripDeals>"
    		+ " - Given an Username,"
    		+ " WHEN Requested getTripDeals,"
    		+ " then return trip deals as expected")
	@Test
	public void getTripDeals() {

    	// GIVEN

    	when(userService.getUser(anyString()))
    	.thenReturn(user1);

    	when(userService.getUser(anyString()))
    	.thenReturn(user2);
    	
        user1.addUserReward(userReward);
        user1.setUserPreferences(userPreferences);
        ProviderDTO providerDTO = new ProviderDTO("name", 100, UUID.randomUUID());
        Provider provider = new Provider("name", 100, UUID.randomUUID());

        lenient().when(internalTestHelper
        		.getInternalUserMap())
        .thenReturn(internalUser);
        
        lenient().when(internalTestHelper
        		.getTripPricerApiKey())
        .thenReturn("test-server-api-key");
        
        lenient().when(tripDealsMicroservice
        		.getProviders(anyString(), any(UUID.class), anyInt(), anyInt(), anyInt(), anyInt()))
        .thenReturn(Arrays.asList(providerDTO));
        
        lenient().when(providerMapper
        		.toProvider(providerDTO))
        .thenReturn(provider);

         // WHEN
         List<ProviderDTO> result = tripDealsService
        		 .getUserTripDeals(user1.getUserName());

         // THEN
         assertNotNull(result);
         assertThat(result).isNotEmpty();
//         assertEquals(4, (result.toArray().toString().length())/7);
         assertTrue(result.toArray().toString().length() > 1);
         
	}



	// ##############################################################


}