package tourGuide.util;

import org.springframework.stereotype.Component;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.UserPreferences;

/**
 * The Class UserPreferencesMapper.
 */
@Component
public class UserPreferencesMapper {
//
//    /**
//     * To user preferences.
//     *
//     * @param userPreferencesDTO the user preferences DTO
//     * @return the user preferences
//     */
//    public UserPreferences toUserPreferences (
//    		UserPreferencesDTO userPreferencesDTO) {
//
//        UserPreferences userPreferences = new UserPreferences();
//
//
//        CurrencyUnit currency;
//
//
//
//    	// ##############################################################
//
//
//        if (userPreferencesDTO.getCurrency() != null) {
//
//        	currency = Monetary
//            		.getCurrency(userPreferencesDTO.getCurrency());
//
//        	userPreferences.setCurrency(Monetary
//        			.getCurrency(userPreferencesDTO.getCurrency()));
//
//        } else {
//
//        	currency = userPreferences.getCurrency();
//
//        }
//
//
//    	// ##############################################################
//
//
//        if (userPreferencesDTO.getAttractionProximity() != null) {
//
//        	userPreferences.setAttractionProximity(userPreferencesDTO
//        			.getAttractionProximity());
//        }
//
//
//    	// ##############################################################
//
//
//
//        if (userPreferencesDTO.getLowerPricePoint() != null) {
//
//        	userPreferences.setLowerPricePoint(Money.of(
//                    userPreferencesDTO.getLowerPricePoint(),
//                    currency));
//        }
//
//
//    	// ##############################################################
//
//
//
//        if (userPreferencesDTO.getHighPricePoint() != null) {
//
//        	userPreferences.setHighPricePoint(Money.of(
//                    userPreferencesDTO.getHighPricePoint(),
//                    currency));
//        }
//
//
//    	// ##############################################################
//
//
//
//        if (userPreferencesDTO.getTripDuration() != null) {
//
//        	userPreferences.setTripDuration(userPreferencesDTO
//            		.getTripDuration());
//        }
//
//
//    	// ##############################################################
//
//
//
//        if (userPreferencesDTO.getTicketQuantity() != null) {
//
//        	userPreferences.setTicketQuantity(userPreferencesDTO
//            		.getTicketQuantity());
//        }
//
//
//    	// ##############################################################
//
//
//
//        if (userPreferencesDTO.getNumberOfAdults() != null) {
//
//        	userPreferences.setNumberOfAdults(userPreferencesDTO
//        			.getNumberOfAdults());
//        }
//
//
//    	// ##############################################################
//
//
//
//        if (userPreferencesDTO.getNumberOfChildren() != null) {
//
//        	userPreferences.setNumberOfChildren(userPreferencesDTO
//            		.getNumberOfChildren());
//        }
//
//        return userPreferences;
//    }


	// ##############################################################



    public UserPreferencesDTO toUserPreferencesDTO(final UserPreferences preferences) {

        return new UserPreferencesDTO(
        		preferences.getAttractionProximity(),
        		preferences.getLowerPricePoint()
                .getNumber().intValue(),
                preferences.getHighPricePoint().getNumber().intValue(),
                preferences.getTripDuration(),
                preferences.getTicketQuantity(),
                preferences.getNumberOfAdults(),
                preferences.getNumberOfChildren());
    }



	// ##############################################################


}
