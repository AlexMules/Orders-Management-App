package utils;

/**
 * Thrown to indicate that a provided email address is invalid.
 */
public class IncorrectEmailException extends Exception {
    public IncorrectEmailException(String message) {
        super(message);
    }
}
