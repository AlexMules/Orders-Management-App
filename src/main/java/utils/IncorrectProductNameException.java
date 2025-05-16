package utils;

/**
 * Thrown to indicate that a provided product name is invalid.
 */
public class IncorrectProductNameException extends Exception {
    public IncorrectProductNameException(String message) {
        super(message);
    }
}
