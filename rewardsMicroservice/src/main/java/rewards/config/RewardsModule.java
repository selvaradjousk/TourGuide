package rewards.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rewardCentral.RewardCentral;


/**
 * The Class RewardsModule.
 */
@Configuration
public class RewardsModule {



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

    /**
	 * Gets the reward central.
	 *
	 * @return the reward central
	 */
	@Bean
    public RewardCentral getRewardCentral() {
        return new RewardCentral();
    }


	// ##############################################################


}
