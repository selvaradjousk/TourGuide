package tourGuide;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class Application.
 */
@SpringBootApplication
public class Application {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	Locale.setDefault(Locale.US);
        SpringApplication.run(Application.class, args);
    }

}
