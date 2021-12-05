package tourGuide.exception;

/**
 * The Class UserNotFoundException.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Instantiates a new user not found exception.
     *
     * @param message the message
     */
    public UserNotFoundException(final String message) {
        super(message);
    }
}
