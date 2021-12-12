package gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Class GpsMicroserviceApplication.
 */
@ComponentScan("gps")
@SpringBootApplication
public class GpsMicroserviceApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(GpsMicroserviceApplication.class, args);
	}

}
