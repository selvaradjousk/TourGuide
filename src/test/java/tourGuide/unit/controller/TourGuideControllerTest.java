package tourGuide.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import tourGuide.dto.LocationDTO;
import tourGuide.dto.NearByAttractionDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.Location;
import tourGuide.service.TourGuideService;

//@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TourGuideControllerTest {

//	@InjectMocks
//	private TourGuideController tourGuideController;
	
    @MockBean
    private TourGuideService tourGuideService;

//    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private LocationDTO userLocationDTO;

    private Location userLocation;

    private Location attractionLocation;

    private ObjectMapper objectMapper;
	
	
    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();

        userLocationDTO = new LocationDTO(33.817595, -117.922008);

        userLocation = new Location(33.817595, -117.922008);

        attractionLocation = new Location(34.817595, -117.922008);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    


	// ##############################################################

    
    @DisplayName("Check (GetNearbyAttractions)"
    		+ " - Given a request,"
    		+ " when GET GetNearbyAttractions,"
    		+ " then return - Status: 200 OK")
    @Test
    public void testGetNearbyAttractionsValidInput() throws Exception {
    
    	UserAttractionRecommendationDTO recommendedAttractionDTO 
    			= new UserAttractionRecommendationDTO(userLocation,

    			Arrays.asList(new NearByAttractionDTO(
                		"Disneyland",
                		attractionLocation,
                        200.00, 500)));
        when(tourGuideService
        		.getUserAttractionRecommendation("testUser"))
        .thenReturn(recommendedAttractionDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getNearbyAttractions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "testUser"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("Disneyland");
        assertThat(content).contains("500");
        verify(tourGuideService).getUserAttractionRecommendation("testUser");
    }

	// ##############################################################
    
    @DisplayName("Check (GetNearbyAttractions) EmptyUserName"
    		+ " - Given a request,"
    		+ " when GET GetNearbyAttractions,"
    		+ " then return - Status: BAD_REQUEST")
    @Test
	public void testGetNearbyAttractionsUrlWithEmptyUserName() throws Exception {
    

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getNearbyAttractions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("username is required");
    }

	// ##############################################################


        @DisplayName("Check (GetNearbyAttractions) Null Parameter"
        		+ " - Given a request,"
        		+ " when GET GetNearbyAttractions,"
        		+ " then return - Status: BAD_REQUEST")
        @Test
    	public void testGetNearbyAttractionsUrlWithNullParam() throws Exception {
        

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            		.get("/getNearbyAttractions")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String content = result
            		.getResponse().getContentAsString();

            assertNotNull(content);
            assertThat(content).contains("userName parameter is missing");
        }


	// ##############################################################

        
        @DisplayName("Check (GetNearbyAttractions) invalid"
        		+ " - Given a request,"
        		+ " when GET GetNearbyAttractions,"
        		+ " then return - Status: 200 OK")
        @Test
        public void testGetNearbyAttractionsInValidInput() throws Exception {
        
        	UserAttractionRecommendationDTO recommendedAttractionDTO 
        			= new UserAttractionRecommendationDTO(userLocation,

        			Arrays.asList(new NearByAttractionDTO(
                    		"Disneyland",
                    		attractionLocation,
                            200.00, 500)));
            when(tourGuideService
            		.getUserAttractionRecommendation("testUser"))
            .thenReturn(recommendedAttractionDTO);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            		.get("/getNearbyAttractions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("userName", "invalidUser"))
                    .andExpect(status().isOk())
                    .andReturn();

            String content = result
            		.getResponse().getContentAsString();

            assertNotNull(content);
       }

    	// ##############################################################
        
    @Test
    @DisplayName("Check (testGetAllCurrentLocations)"
    		+ " - Given a request,"
    		+ " when GET testGetAllCurrentLocations,"
    		+ " then return - Status: 200 OK")
    public void testGetAllCurrentLocations() throws Exception {

    	Map<String, LocationDTO> usersLocation = new HashMap<>();

    	usersLocation.put(
    			UUID.randomUUID().toString()
    			, userLocationDTO);
    	
        when(tourGuideService
        		.getAllUserRecentLocation())
        .thenReturn(usersLocation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getAllCurrentLocations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        
        assertNotNull(content);
        assertThat(content).contains("33.817595");
        assertThat(content).contains("-117.922008");
        verify(tourGuideService).getAllUserRecentLocation();
    }  
    


 	// ##############################################################

    @Test
    @DisplayName("Check (GetTripDeals)"
    		+ " - Given an username,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: 200 OK")
    public void testGetTripDeals() throws Exception {

    	List<ProviderDTO> providers = Arrays.asList(
    			new ProviderDTO("testProviderName",
    					500, UUID.randomUUID()));

        when(tourGuideService
        		.getUserTripDeals("testUser"))
        .thenReturn(providers);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getTripDeals")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "testUser"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("testProviderName");
        assertThat(content).contains("500");
        verify(tourGuideService).getUserTripDeals("testUser");
    }


	// ##############################################################


    @Test
    @DisplayName("Check (GetTripDeals) empty username"
    		+ " - Given an empty username,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: BAD_REQUEST")
	public void testGetTripDealsUrlWithEmptyUserName() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getTripDeals")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("username is required");
    }



	// ##############################################################


    @Test
    @DisplayName("Check (GetTripDeals) null value"
    		+ " - Given an null value for username,"
    		+ " when GET tripDeals,"
    		+ " then return - Status: BAD_REQUEST")
	public void testGetTripDealsUrlWithNullUserName() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getTripDeals")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("userName parameter is missing");
    }



	// ##############################################################


    @Test
    @DisplayName("Check (updateUserPreferences)"
    		+ " - Given an user preference to update,"
    		+ " when PUT updateUserPreferences,"
    		+ " then return - Status: 201 OK update done")
    public void testUpdateUserPreferences() throws Exception {

    	UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO(999,
                999, 999, 999, 999, 999, 999);

        when(tourGuideService
        		.updateUserPreferences(anyString(), any(UserPreferencesDTO.class)))
        .thenReturn(userPreferencesDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.put("/updateUserPreferences")
                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(userPreferencesDTO))
	                .param("userName", "testUser"))
			        .andExpect(status().is2xxSuccessful())
			        .andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertThat(content).contains("999");
    }



	// ##############################################################



    @Test
    @DisplayName("Check (updateUserPreferences) empty username - "
    		+ " - Given an user preference to update without username,"
    		+ " when PUT updateUserPreferences,"
    		+ " then return - Status: BAD_REQUEST")
    public void testUpdateUserPreferencesEmptyUserName() throws Exception {

    	UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO(999,
                999, 999, 999, 999, 999, 999);

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/updateUserPreferences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPreferencesDTO))
                .param("userName", ""))
                .andExpect(status().isBadRequest());
    }


	// ##############################################################

    @Test
    @DisplayName("Check (updateUserPreferences) empty body"
    		+ " - Given an user preference to update with no body,"
    		+ " when PUT updateUserPreferences,"
    		+ " then return - Status: BAD_REQUEST")
    public void testUpdateUserPreferencesEmptyBody() throws Exception {
    
    	mockMvc.perform(MockMvcRequestBuilders
        		.put("/updateUserPreferences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(""))
                .param("userName", "testUser"))
                .andExpect(status().isBadRequest());
    }



	// ##############################################################

    
    }
