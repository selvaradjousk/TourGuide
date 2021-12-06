package tourGuide.unit.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.money.Monetary;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.UserPreferences;
import tourGuide.util.UserPreferencesMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@ActiveProfiles("test")
public class TestUserPreferenceMapper {

    private UserPreferencesMapper userPreferencesMapper = new UserPreferencesMapper();

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
    
    @DisplayName("Check <testToUserPreferences>"
    		+ " - Given DTO,"
    		+ " when toUserPreferences,"
    		+ " then return DO expected")
	@Test
	public void testToUserPreferences() {

		
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setTripDuration(999);
        userPreferences.setTicketQuantity(999);
        userPreferences.setNumberOfAdults(999);
        userPreferences.setNumberOfChildren(999);
        userPreferences.setHighPricePoint(Money.of(999, Monetary.getCurrency("EUR")));
        userPreferences.setLowerPricePoint(Money.of(999, Monetary.getCurrency("EUR")));
        userPreferences.setAttractionProximity(999);
    	

    	UserPreferences testUserPreferences = new UserPreferences();
    	testUserPreferences.setTripDuration(999);
    	testUserPreferences.setTicketQuantity(999);
    	testUserPreferences.setNumberOfAdults(999);
    	testUserPreferences.setNumberOfChildren(999);
    	testUserPreferences.setHighPricePoint(Money.of(999, Monetary.getCurrency("EUR")));
    	testUserPreferences.setLowerPricePoint(Money.of(999, Monetary.getCurrency("EUR")));
    	testUserPreferences.setAttractionProximity(999);
    	


        UserPreferencesDTO result = userPreferencesMapper.toUserPreferencesDTO(userPreferences);

        assertEquals(testUserPreferences.getTripDuration(), result.getTripDuration());
        assertEquals(testUserPreferences.getTicketQuantity(), result.getTicketQuantity());
        assertEquals(testUserPreferences.getNumberOfAdults(), result.getNumberOfAdults());
        assertEquals(testUserPreferences.getNumberOfChildren(), result.getNumberOfChildren());
        assertEquals(testUserPreferences.getAttractionProximity(), result.getAttractionProximity());
	}



	// ##############################################################

    
	@Test
	    public void testForAllInputsValid() {
	 
	    	// GIVEN
			UserPreferencesDTO userPreferencesDtoOK = new UserPreferencesDTO(999, 999, 999, 999, 999, 999, 999);
			
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
    	UserPreferencesDTO userPreferencesDtoProximityInvalid = new UserPreferencesDTO(-10, 999, 999, 999, 999, 999, 999);
    	
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
    	UserPreferencesDTO userPreferencesDtoLowerPriceInvalid = new UserPreferencesDTO(999, -999, 999, 999, 999, 999, 999);
    	
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
    	UserPreferencesDTO userPreferencesDtoHighPriceInvalid = new UserPreferencesDTO(999, 999, -999, 999, 999, 999, 999);
    	
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
			UserPreferencesDTO userPreferencesDtoTripDurationZero = new UserPreferencesDTO(999, 999, 999, 0, 999, 999, 999);
			
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
    	UserPreferencesDTO userPreferencesDtoTripDurationInvalid = new UserPreferencesDTO(999, 999, 999, -999, 999, 999, 999);
    	
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
		UserPreferencesDTO userPreferencesDtoTicketQuantityZero = new UserPreferencesDTO(999, 999, 999, 999, 0, 999, 999);
		
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
    	UserPreferencesDTO userPreferencesDtoTicketQuantityInvalid = new UserPreferencesDTO(999, 999, 999, 999, -999, 999, 999);
    	
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
    	UserPreferencesDTO userPreferencesDtoAdultNumberZero = new UserPreferencesDTO(999, 999, 999, 999, 999, 0, 999);
    	
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
    	UserPreferencesDTO userPreferencesDtoAdultNumberInvalid = new UserPreferencesDTO(999, 999, 999, 999, 999, -999, 999);
    	
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
    	UserPreferencesDTO userPreferencesDtoChildrenNumberInvalid = new UserPreferencesDTO(999, 999, 999, 999, 999, 999, -999);
    	
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
