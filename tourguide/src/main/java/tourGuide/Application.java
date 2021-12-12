package tourGuide;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Class Application.
 */
@EnableFeignClients("tourGuide")
@SpringBootApplication
@ComponentScan("tourGuide")
public class Application {




	// ##############################################################




    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	Locale.setDefault(Locale.US);
        SpringApplication.run(Application.class, args);
    }




	// ##############################################################




}
