package tourGuide.util;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.UserPreferences;

@Component
public class UserPreferencesMapper {

    public UserPreferences toUserPreferences (
    		UserPreferencesDTO userPreferencesDTO) {

        UserPreferences userPreferences = new UserPreferences();


        CurrencyUnit currency;



    	// ##############################################################


        if (userPreferencesDTO.getCurrency() != null) {

        	currency = Monetary
            		.getCurrency(userPreferencesDTO.getCurrency());

        	userPreferences.setCurrency(Monetary
        			.getCurrency(userPreferencesDTO.getCurrency()));

        } else {

        	currency = userPreferences.getCurrency();

        }


    	// ##############################################################


        if (userPreferencesDTO.getAttractionProximity() != null) {

        	userPreferences.setAttractionProximity(userPreferencesDTO
        			.getAttractionProximity());
        }


    	// ##############################################################



        if (userPreferencesDTO.getLowerPricePoint() != null) {

        	userPreferences.setLowerPricePoint(Money.of(
                    userPreferencesDTO.getLowerPricePoint(),
                    currency));
        }


    	// ##############################################################



        if (userPreferencesDTO.getHighPricePoint() != null) {

        	userPreferences.setHighPricePoint(Money.of(
                    userPreferencesDTO.getHighPricePoint(),
                    currency));
        }


    	// ##############################################################



        if (userPreferencesDTO.getTripDuration() != null) {

        	userPreferences.setTripDuration(userPreferencesDTO
            		.getTripDuration());
        }


    	// ##############################################################



        if (userPreferencesDTO.getTicketQuantity() != null) {

        	userPreferences.setTicketQuantity(userPreferencesDTO
            		.getTicketQuantity());
        }


    	// ##############################################################



        if (userPreferencesDTO.getNumberOfAdults() != null) {

        	userPreferences.setNumberOfAdults(userPreferencesDTO
        			.getNumberOfAdults());
        }


    	// ##############################################################



        if (userPreferencesDTO.getNumberOfChildren() != null) {

        	userPreferences.setNumberOfChildren(userPreferencesDTO
            		.getNumberOfChildren());
        }

        return userPreferences;
    }


	// ##############################################################



}
