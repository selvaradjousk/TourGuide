package gps.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;

@Configuration
public class GpsModule {


	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(GpsModule.class);


	// ##############################################################


	/**
	 * Gets the gps util.
	 *
	 * @return the gps util
	 */
	@Bean
	public GpsUtil getGpsUtil() {

        logger.info("## getGpsUtil() BEAN invoked");

		return new GpsUtil();
	}



	// ##############################################################


}
