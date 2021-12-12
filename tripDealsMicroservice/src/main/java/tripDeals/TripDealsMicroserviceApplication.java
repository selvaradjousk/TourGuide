package tripDeals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Class TripDealsMicroserviceApplication.
 */
@ComponentScan("tripDeals")
//@EnableAutoConfiguration
//@EnableWebMvc
//@Configuration
@SpringBootApplication
public class TripDealsMicroserviceApplication {

	// ##############################################################

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TripDealsMicroserviceApplication.class, args);
	}

	// ##############################################################

}
