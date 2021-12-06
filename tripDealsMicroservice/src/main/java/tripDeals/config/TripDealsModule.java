package tripDeals.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tripPricer.TripPricer;

/**
 * The Class TripDealsModule.
 */
@Configuration
public class TripDealsModule {



	//###############################################################


    
    /**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	@Bean
    public Locale getLocale() {
        Locale.setDefault(Locale.US);
        return Locale.getDefault();

    }

     //###############################################################


    /**
      * Gets the trip pricer.
      *
      * @return the trip pricer
      */
     @Bean
    public TripPricer getTripPricer() {
        return new TripPricer();
    }


    //###############################################################


}
