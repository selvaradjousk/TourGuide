package tourGuide.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
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

import tourGuide.dto.ProviderDTO;
import tourGuide.service.GpsLocationService;

@DisplayName("UNIT TESTS - Controller - TourGuide")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TripDealsControllerTest {

	
    @MockBean
    private GpsLocationService tourGuideService;

//    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

	
	
    @BeforeEach
    public void setUp() {


        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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

        
    }
