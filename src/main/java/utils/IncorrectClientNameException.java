package utils;

/**
 * Thrown to indicate that a provided client name is invalid.
 */
public class IncorrectClientNameException extends Exception {
    public IncorrectClientNameException(String message) {
        super(message);
    }
}
