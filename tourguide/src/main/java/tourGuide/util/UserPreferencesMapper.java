package tourGuide.util;

import org.springframework.stereotype.Component;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.UserPreferences;

/**
 * The Class UserPreferencesMapper.
 */
@Component
public class UserPreferencesMapper {



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
