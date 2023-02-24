package tourGuide.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class GlobalExceptionHandler.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);



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

    	logger.error("Request failed :", ex);

    	ErrorDetails errorDetails = new ErrorDetails(
    			LocalDateTime.now(),
    			ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(
        		errorDetails,
        		HttpStatus.NOT_FOUND);
    }



	// ##############################################################

    /**
	 * Handle conflict.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(DataAlreadyRegisteredException.class)
    public ResponseEntity handleConflict(
    		final DataAlreadyRegisteredException ex,
    		final WebRequest request) {

    	logger.error("Request failed :", ex);

        ErrorDetails errorDetails = new ErrorDetails(
        		LocalDateTime.now(),
        		ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(
        		errorDetails,
        		HttpStatus.CONFLICT);
    }



	// ##############################################################

    /**
	 * Handle bad request.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequest(
    		final BadRequestException ex,
    		final WebRequest request) {

    	logger.error("Request failed :", ex);

        ErrorDetails errorDetails = new ErrorDetails(
        		LocalDateTime.now(),
        		ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(
        		errorDetails,
        		HttpStatus.BAD_REQUEST);
    }



	// ##############################################################

    /**
	 * Handle method argument not valid.
	 *
	 * @param ex the ex
	 * @param header the header
	 * @param status the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
    public ResponseEntity handleMethodArgumentNotValid(
    		final MethodArgumentNotValidException ex,
    		final HttpHeaders header,
    		final HttpStatus status, final WebRequest request) {

    	logger.error("Request failed :", ex);

        // Error messages for invalid fields
        String errorMessage = ex.getBindingResult()
        		.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        // ErrorResponse for Validation on error messages and requests
        ErrorDetails errorDetails = new ErrorDetails(
        		LocalDateTime.now(),
        		errorMessage,
        		request.getDescription(false));

        return new ResponseEntity<Object>(
        		errorDetails,
        		HttpStatus.BAD_REQUEST);
    }



	// ##############################################################

    /**
	 * Handle missing servlet request parameter.
	 *
	 * @param ex the ex
	 * @param headers the headers
	 * @param status the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

    	logger.error("Request failed - Missing parameter:"
    			+ " {}", ex.getParameterName());

        final String error = ex.getParameterName()
        		+ " parameter is missing";

        final ErrorDetails errorDetails = new ErrorDetails(
        		LocalDateTime.now(),
        		ex.getLocalizedMessage(),
        		error);

        return new ResponseEntity<>(
        		errorDetails,
        		HttpStatus.BAD_REQUEST);
    }

	// ##############################################################

}
