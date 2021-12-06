package tripDeals.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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

import tripDeals.controller.TripDealsController;
import tripDeals.dto.ProviderDTO;
import tripDeals.service.ITripDealsService;

@DisplayName("UNIT TESTS - Controller - TripDeals - Microservice")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TripDealsController.class)
public class TripDealsControllerTest {

	

	@MockBean
	private ITripDealsService tripDealsService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	private UUID userId;
	
	private ProviderDTO providerDTO1;
	
	private ProviderDTO providerDTO2;
	
	private List<ProviderDTO> providers;
	
	// URL
	private final static String PROVIDERS_URL = "/tripDeals/providers/test-server-api-key/9732e7e3-943e-4169-97e1-6f171b0e86f0/999/999/999/999";



	//###############################################################


    
	@BeforeEach
	public void setUp() {
	
	    userId = UUID.fromString("9732e7e3-943e-4169-97e1-6f171b0e86f0");
	    providerDTO1 = new ProviderDTO("testName1", 100, UUID.randomUUID());
	    providerDTO2 = new ProviderDTO("testName2", 100, UUID.randomUUID());
	
	    providers = Arrays.asList(providerDTO1, providerDTO2);
	
	    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}



	//###############################################################


    
	@Test
    @DisplayName("Check (getProviders) "
    		+ " - Given an User Preferences,"
    		+ " when GET getProviders,"
    		+ " then return - response OK")
    public void testGetProviders() throws Exception {
		
		when(tripDealsService
				.getProviders(
						anyString(),
						any(UUID.class),
						anyInt(),
						anyInt(),
						anyInt(),
						anyInt()))
		.thenReturn(providers);
	
	    MvcResult result = mockMvc.perform(
	    		MockMvcRequestBuilders
	    		.get(PROVIDERS_URL)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
	
	    String content = result.getResponse().getContentAsString();
	
	    assertNotNull(content);
	    assertThat(content).contains("testName1");
	    assertThat(content).contains("testName2");
	    verify(tripDealsService).getProviders(anyString(), any(UUID.class), anyInt(), anyInt(), anyInt(), anyInt());
	}



	//###############################################################


    
	@Test
    @DisplayName("Check (getProviders) invalid path parameters"
    		+ " - Given an User Preferences,"
    		+ " when GET getProviders,"
    		+ " then return - response NOT_FOUND")
    public void testGetProvidersInvalidPathParameter() throws Exception {

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/tripDeals/providers/" +
	            "test-server-api-key/9732e7e3-943e-4169-97e1-6f171b0e86f0/999/999/999/999")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound())
	            .andReturn();
	
	    String content = result.getResponse().getContentAsString();
	
	}



	//###############################################################


    @DisplayName("Check (getProviders) empty provider list"
    		+ " - Given an User Preferences, empty provider list "
    		+ " when GET getProviders,"
    		+ " then return - response NOT_FOUND")
	@Test
	public void getProvidersRequestEmptyList() throws Exception {

    	when(tripDealsService
	    		.getProviders(
	    				anyString(),
	    				any(UUID.class),
	    				anyInt(),
	    				anyInt(),
	    				anyInt(),
	    				anyInt()))
	    .thenReturn(Collections.emptyList());
	
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
	    		.get(PROVIDERS_URL)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound())
	            .andReturn();

	    String content = result.getResponse().getContentAsString();
	
	    assertThat(content).contains("unable to get provider list");
	}



	//###############################################################


    

}
