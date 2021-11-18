package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.UserPreferences;
import tourGuide.util.UserPreferencesMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestUserPreferenceMapper {

    private UserPreferencesMapper userPreferencesMapper = new UserPreferencesMapper();

	@Test
	public void testToUserPreferences() {

	
        UserPreferencesDTO userPreferencesDto = new UserPreferencesDTO();
        userPreferencesDto.setTripDuration(999);
        userPreferencesDto.setTicketQuantity(999);
        userPreferencesDto.setNumberOfAdults(999);
        userPreferencesDto.setNumberOfChildren(999);
        userPreferencesDto.setCurrency(Monetary.getCurrency("EUR").toString());
        userPreferencesDto.setHighPricePoint(999);
        userPreferencesDto.setLowerPricePoint(999);
        userPreferencesDto.setAttractionProximity(999);
    	

    	UserPreferences testUserPreferences = new UserPreferences();
    	testUserPreferences.setTripDuration(999);
    	testUserPreferences.setTicketQuantity(999);
    	testUserPreferences.setNumberOfAdults(999);
    	testUserPreferences.setNumberOfChildren(999);
    	testUserPreferences.setCurrency(Monetary.getCurrency("EUR"));
    	testUserPreferences.setHighPricePoint(Money.of(999, Monetary.getCurrency("EUR")));
    	testUserPreferences.setLowerPricePoint(Money.of(999, Monetary.getCurrency("EUR")));
    	testUserPreferences.setAttractionProximity(999);
    	


        UserPreferences result = userPreferencesMapper.toUserPreferences(userPreferencesDto);

        assertThat(result).isEqualToComparingFieldByField(testUserPreferences);
	}



	// ##############################################################




}
