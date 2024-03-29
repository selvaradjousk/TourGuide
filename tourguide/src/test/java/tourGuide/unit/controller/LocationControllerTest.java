package tourGuide.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
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

import tourGuide.dto.LocationDTO;
import tourGuide.dto.NearByAttractionDTO;
import tourGuide.dto.UserAttractionRecommendationDTO;
import tourGuide.model.Location;
import tourGuide.service.GpsLocationService;

@DisplayName("UNIT TESTS - Controller - Location")
//@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LocationControllerTest {

	
    @MockBean
    private GpsLocationService tourGuideService;

//    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private LocationDTO userLocationDTO;

    private Location userLocation;

    private Location attractionLocation;

	
	
    @BeforeEach
    public void setUp() {

        userLocationDTO = new LocationDTO(33.817595, -117.922008);

        userLocation = new Location(33.817595, -117.922008);

        attractionLocation = new Location(34.817595, -117.922008);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    

	// ##############################################################

    @Test
    @DisplayName("Check (GetLocation) Valid "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: 200 OK")
	public void testGetLocationUrlValid() throws Exception {

    	when(tourGuideService
    			.getUserLocation("testUser"))
    	.thenReturn(userLocationDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getLocation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "testUser"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("33.817595");
        assertThat(content).contains("-117.922008");
        verify(tourGuideService).getUserLocation(anyString());
    }

	// ##############################################################

    
    @Test
    @DisplayName("Check (GetLocation) EmptyUserName "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: BAD_REQUEST")
	public void testGetLocationUrlWithEmptyUserName() throws Exception {

    	when(tourGuideService
    			.getUserLocation("testUser"))
    	.thenReturn(userLocationDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getLocation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("username is required");
    }


	// ##############################################################

    
    @Test
    @DisplayName("Check (GetLocation) missing parameter "
    		+ " - Given a request,"
    		+ " when GET GetLocation,"
    		+ " then return - Status: BAD_REQUEST")
	public void testGetLocationUrlWithMissingParameter() throws Exception {

    	when(tourGuideService
    			.getUserLocation("testUser"))
    	.thenReturn(userLocationDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getLocation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("userName parameter is missing");
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

        
    }
