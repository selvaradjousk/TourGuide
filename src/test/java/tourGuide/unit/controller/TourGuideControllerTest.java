package tourGuide.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
