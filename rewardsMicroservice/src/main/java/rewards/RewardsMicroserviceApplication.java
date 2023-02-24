package rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Class RewardsMicroserviceApplication.
 */
@ComponentScan("rewards")
@SpringBootApplication
public class RewardsMicroserviceApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RewardsMicroserviceApplication.class, args);
	}

}
