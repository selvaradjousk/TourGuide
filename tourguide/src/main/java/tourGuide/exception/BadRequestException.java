package tourGuide.exception;

/**
 * The Class BadRequestException.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Instantiates a new bad request exception.
     *
     * @param message the message
     */
    public BadRequestException(final String message) {
        super(message);
    }
}
