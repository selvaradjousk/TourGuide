package gps.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * The Class GlobalExceptionHandler.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * GlobalExceptionHandler logger.
     */
	private Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);



	// ##############################################################


    /**
	 * Handle type mismatch.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleTypeMismatch(
    		final MethodArgumentTypeMismatchException ex,
    		final WebRequest request) {

    	logger.error("Request - FAILED : Invalid UUID");

        ErrorDetails errorDetails = new ErrorDetails(
        		LocalDateTime.now(),
        		ex.getMessage(),
                request.getDescription(false));

        errorDetails.setMessage("Invalid UUID string");

        return new ResponseEntity<>(
        		errorDetails,
        		HttpStatus.BAD_REQUEST);
    }



	// ##############################################################


    /**
	 * Handle missing path variable.
	 *
	 * @param ex the ex
	 * @param headers the headers
	 * @param status the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
    protected ResponseEntity<Object> handleMissingPathVariable(
    		final MissingPathVariableException ex,
    		final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
       logger.error("Request - FAILED : Missing path variable: {}",
    		   ex.getVariableName());

        String error = ex.getVariableName() + " parameter is missing";

        ErrorDetails errorDetails = new ErrorDetails(
        		LocalDateTime.now(),
        		ex.getLocalizedMessage(), error);

        return new ResponseEntity<>(
        		errorDetails,
        		HttpStatus.BAD_REQUEST);
    }


	// ##############################################################



    /**
	 * Handle not found.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleNotFound(
    		final UserNotFoundException ex,
    		final WebRequest request) {

    	logger.error("Request - FAILED :", ex);
        ErrorDetails errorDetails = new ErrorDetails(
        		LocalDateTime.now(),
        		ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(
        		errorDetails,
        		HttpStatus.NOT_FOUND);
    }


	// ##############################################################


}
