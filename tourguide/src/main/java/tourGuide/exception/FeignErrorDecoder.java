package tourGuide.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.Response;
import feign.codec.ErrorDecoder;


public class FeignErrorDecoder implements ErrorDecoder {



	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(FeignErrorDecoder.class);



	// ##############################################################



    private final ErrorDecoder defaultErrorDecoder = new Default();


    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {

        	logger.error("Feign client Error - Status code: "
                    + response.status() + ", methodKey = " + methodKey);
            return new ResourceNotFoundException("Resource not found");
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
