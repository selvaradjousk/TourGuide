package rewards.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import rewards.controller.RewardsController;
import rewards.service.IRewardsService;


@DisplayName("UNIT TESTS - Controller - Rewards - Microservice")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RewardsController.class)
class RewardsControllerTest {

    @MockBean
    private IRewardsService rewardsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private UUID userId, emptyUserId;

    private UUID attractionId;

    @BeforeEach
    public void setUp() {

        userId = UUID.fromString("9732e7e3-943e-4169-97e1-6f171b0e86f0");
        attractionId = UUID.fromString("cb2df5f1-0305-4f43-ad12-c664238cf411");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }



	//###############################################################


    
    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) "
    		+ " - Given an user & attraction id,"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: 200 OK")
     public void testGetAttractionRewardPoints() throws Exception {

    	when(rewardsService
    			.getAttractionRewardPoints(attractionId, userId))
    	.thenReturn(500);

        MvcResult result = mockMvc.perform(
        		MockMvcRequestBuilders
        		.get("/rewards/points/" + attractionId + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("500");
        verify(rewardsService).getAttractionRewardPoints(attractionId, userId);
    }




	//###############################################################


	
    
    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) without userId "
    		+ " - Given an user & attraction id, without user Id"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsWithoutUserId() throws Exception {


        MvcResult result = mockMvc.perform(
        		MockMvcRequestBuilders
        		.get("/rewards/points/" + attractionId + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);

    }




	//###############################################################



    
    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) without Ids input "
    		+ " - Given an user & attraction id, without input Ids"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsWithoutParameters() throws Exception {


        MvcResult result = mockMvc.perform(
        		MockMvcRequestBuilders
        		.get("/rewards/points/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);

    }




	//###############################################################



    
    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) null Ids input "
    		+ " - Given an user & attraction id, null Ids"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsNullInput() throws Exception {


        MvcResult result = mockMvc.perform(
        		MockMvcRequestBuilders
        		.get("/rewards/points/" + null + "/" + null)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("Invalid UUID string");
        

    }




	//###############################################################



    
    
    @Test
    @DisplayName("Check (getAttractionRewardPoints) invalid input "
    		+ " - Given an user & attraction id, invalid Ids"
    		+ " when GET getAttractionRewardPoints,"
    		+ " then return - Status: BAD_REQUEST")
     public void testGetAttractionRewardPointsInvalidInput() throws Exception {


        MvcResult result = mockMvc.perform(
        		MockMvcRequestBuilders
        		.get("/rewards/points/" + "9732e7e3" + "/" + "9732e7e3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("Invalid UUID string");
        

    }




	//###############################################################


			
	
    
}
