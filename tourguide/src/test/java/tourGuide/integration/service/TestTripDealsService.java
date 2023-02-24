package tourGuide.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.ProviderDTO;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.service.TripDealsService;
import tourGuide.service.UserService;

@DisplayName("IT - Service - TripDeals")
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest("/integration-test.properties")
public class TestTripDealsService {

  
    
    @Autowired
    UserService userService;
    
    @Autowired
    TripDealsService tripDealsService;


	// ##############################################################



    @DisplayName("Check <getTripDeals>"
    		+ " - Given an Username,"
    		+ " WHEN Requested getTripDeals,"
    		+ " then return trip deals as expected")
	@Test
	public void getTripDeals() {

    	// GIVEN
    	User user = userService.getUser("internalUser1");
        user.setUserPreferences(new UserPreferences(
        		 10,
        		 Money.of(500, Monetary.getCurrency("USD")),
        		 Money.of(1000, Monetary.getCurrency("USD")),
                 5,
                 5,
                 2,
                 3));

         // WHEN
         List<ProviderDTO> result = tripDealsService
        		 .getUserTripDeals("internalUser1");

         // THEN
         assertNotNull(result);
         assertThat(result).isNotEmpty();
         assertEquals(5, result.size());
	}



	// ##############################################################



}
