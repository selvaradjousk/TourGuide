package tourGuide.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.service.UserService;

@DisplayName("UNIT TESTS - Controller - User")
//@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserGuideControllerTest {

	
    @MockBean
    private UserService userService;

//    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper;
	
	
    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();


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


    @DisplayName("Check (GetUser) "
    		+ " - Given a request,"
    		+ " when GET GetUser,"
    		+ " then return - Status: 200 OK")
	@Test
	public void testGetUser() throws Exception {
	

       MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        		.get("/getUser")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "testUser"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result
        		.getResponse().getContentAsString();

        assertNotNull(content);
        

    }

	// ##############################################################

        
    }
