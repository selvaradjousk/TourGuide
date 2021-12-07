package tourGuide.config;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;
import tourGuide.exception.FeignErrorDecoder;

/**
 * The Class TourGuideModule.
 */
@Configuration
public class TourGuideModule {


//	/** The logger. */
//	private Logger logger = LoggerFactory
//			.getLogger(TourGuideModule.class);


	// ##############################################################


	/**
	 * Gets the executor service.
	 *
	 * @return the executor service
	 */
	@Bean
	public ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(1000);
	}


	// ##############################################################

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
    


	// ##############################################################

//    /**
//	 * Gets the trip pricer.
//	 *
//	 * @return the trip pricer
//	 */
//	@Bean
//    public TripPricer getTripPricer() {
//        return new TripPricer();
//    }  

	// ##############################################################


    @Bean
    public FeignErrorDecoder myErrorDecoder() {
        return new FeignErrorDecoder();
    }

	// ##############################################################

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

	// ##############################################################

}
