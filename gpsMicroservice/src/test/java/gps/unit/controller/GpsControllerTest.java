package gps.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import gps.controller.GpsController;
import gps.dto.VisitedLocationDTO;
import gps.model.Location;
import gps.service.IGpsService;

@DisplayName("UNIT TESTS - Controller - GPS - Microservice")
@ExtendWith(SpringExtension.class)
@WebMvcTest(GpsController.class)
class GpsControllerTest {

    @MockBean
    private IGpsService gpsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    // URL
    private final static String ATTRACTIONS_URL = "/gps/attractions";

    private final static String USER_LOCATION_URL = "/gps/userLocation/";


	//###############################################################


	
    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


	//###############################################################


	

    @Test
    @DisplayName("Check (getUserLocation) "
    		+ " - Given an userId,"
    		+ " when GET getUserLocation URL,"
    		+ " then return - Status: 200 OK")
     public void testGetUserLocationRequest() throws Exception {

    	UUID userId = UUID.fromString("6551af94-3263-4253-8600-e9add493f508");

    	VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(
    			userId,
    			new Location( 111.021844, -54.18879),
    			new Date());

    	when(gpsService
    			.getUserLocation(userId))
    	.thenReturn(visitedLocationDTO);

        MvcResult result = mockMvc
        		.perform(MockMvcRequestBuilders
        				.get(USER_LOCATION_URL + userId)
        				.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains(userId.toString());
        verify(gpsService).getUserLocation(userId);
    }



	//###############################################################


	

    @Test
    @DisplayName("Check (getUserLocation) with empty input"
    		+ " - Given an userId,"
    		+ " when GET getUserLocation URL,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetUserLocationRequestWithEmptyInput() throws Exception {

        MvcResult result = mockMvc
        		.perform(MockMvcRequestBuilders
        				.get(USER_LOCATION_URL + "")
        				.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isBadRequest())
        		.andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("userId parameter is missing");
    }





	//###############################################################


	
}
