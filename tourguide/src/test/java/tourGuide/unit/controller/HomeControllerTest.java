package tourGuide.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import tourGuide.service.TourGuideService;

@DisplayName("UNIT TESTS - Controller - Home")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeControllerTest {

	
    @MockBean
    private TourGuideService tourGuideService;

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
    @DisplayName("Check (testIndexPageUrl) "
    		+ " - Given a request,"
    		+ " when GET index page URL,"
    		+ " then return - Status: 200 OK")
	public void testIndexPageUrl() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        assertThat(content).contains("Greetings from TourGuide!");
    }
    
    
    
	// ##############################################################

        
    }
