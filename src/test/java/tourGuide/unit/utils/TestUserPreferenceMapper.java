package tourGuide.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.money.Monetary;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
//@ActiveProfiles("test")
public class TestUserPreferenceMapper {

    private UserPreferencesMapper userPreferencesMapper = new UserPreferencesMapper();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
    
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

	@Test
	    public void testForAllInputsValid() {
	 
	    	// GIVEN
			UserPreferencesDTO userPreferencesDtoOK = new UserPreferencesDTO("testUser", 999, 999, 999, 999, 999, 999, 999);
			
	        // WHEN
	        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
	                validator.validate(userPreferencesDtoOK);
	        // THEN
	        assertEquals(0, constraintViolations.size());

	    }
		

	// ##############################################################
		
    @Test
    public void testForAttractionProximityInvalid() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoProximityInvalid = new UserPreferencesDTO("testUser", -10, 999, 999, 999, 999, 999, 999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoProximityInvalid);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Valid value required",
        constraintViolations.iterator().next().getMessage());
    }
	
	// ##############################################################
	
    @Test
    public void testForLowerPricePointInvalid() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoLowerPriceInvalid = new UserPreferencesDTO("testUser", 999, -999, 999, 999, 999, 999, 999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoLowerPriceInvalid);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Valid value required",
        constraintViolations.iterator().next().getMessage());
    }
			

	// ##############################################################
	
    @Test
    public void testForHighPricePointInvalid() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoHighPriceInvalid = new UserPreferencesDTO("testUser", 999, 999, -999, 999, 999, 999, 999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoHighPriceInvalid);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Valid value required",
        constraintViolations.iterator().next().getMessage());
    }
			
	

// ##############################################################

		@Test
		public void testForTripDurationZero() {
		
			// GIVEN
			UserPreferencesDTO userPreferencesDtoTripDurationZero = new UserPreferencesDTO("testUser", 999, 999, 999, 0, 999, 999, 999);
			
		    // WHEN
		    Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
		            validator.validate(userPreferencesDtoTripDurationZero);
		    // THEN
		    assertEquals(1, constraintViolations.size());
		    assertEquals("Minimum stay period should be atleast 1",
		    constraintViolations.iterator().next().getMessage());
		}
		
		

	// ##############################################################
	
    @Test
    public void testForTripDurationInvalid() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoTripDurationInvalid = new UserPreferencesDTO("testUser", 999, 999, 999, -999, 999, 999, 999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoTripDurationInvalid);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Minimum stay period should be atleast 1",
        constraintViolations.iterator().next().getMessage());
    }
			
	

// ##############################################################

		
		@Test
		public void testForTicketQuantityZero() {
		
		// GIVEN
		UserPreferencesDTO userPreferencesDtoTicketQuantityZero = new UserPreferencesDTO("testUser", 999, 999, 999, 999, 0, 999, 999);
		
		// WHEN
		Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
		    validator.validate(userPreferencesDtoTicketQuantityZero);
		// THEN
		assertEquals(1, constraintViolations.size());
		assertEquals("Minimum ticket quantity value (1) required",
		constraintViolations.iterator().next().getMessage());
		}

				

	// ##############################################################
		
	
    @Test
    public void testForTicketQuantityInvalid() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoTicketQuantityInvalid = new UserPreferencesDTO("testUser", 999, 999, 999, 999, -999, 999, 999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoTicketQuantityInvalid);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Minimum ticket quantity value (1) required",
        constraintViolations.iterator().next().getMessage());
    }
			
				

	// ##############################################################
		
	
    @Test
    public void testForTicketAdultNumberZero() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoAdultNumberZero = new UserPreferencesDTO("testUser", 999, 999, 999, 999, 999, 0, 999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoAdultNumberZero);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Minimum number of adults (1) required",
        constraintViolations.iterator().next().getMessage());
    }
			
			

	// ##############################################################
		
		
    @Test
    public void testForTicketAdultNumberInvalid() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoAdultNumberInvalid = new UserPreferencesDTO("testUser", 999, 999, 999, 999, 999, -999, 999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoAdultNumberInvalid);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Minimum number of adults (1) required",
        constraintViolations.iterator().next().getMessage());
    }
			
			

	// ##############################################################
		
	
	
    @Test
    public void testForTicketChildrenNumberInvalid() {
 
    	// GIVEN
    	UserPreferencesDTO userPreferencesDtoChildrenNumberInvalid = new UserPreferencesDTO("testUser", 999, 999, 999, 999, 999, 999, -999);
    	
        // WHEN
        Set<ConstraintViolation<UserPreferencesDTO>> constraintViolations =
                validator.validate(userPreferencesDtoChildrenNumberInvalid);
        // THEN
        assertEquals(1, constraintViolations.size());
        assertEquals("Valid value required",
        constraintViolations.iterator().next().getMessage());
    }
			
			
	// ##############################################################
		

}
