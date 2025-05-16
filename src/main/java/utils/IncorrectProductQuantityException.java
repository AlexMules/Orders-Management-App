package utils;

/**
 * Thrown to indicate that a provided product quantity is invalid.
 */
public class IncorrectProductQuantityException extends Exception {
    public IncorrectProductQuantityException(String message) {
        super(message);
    }
}
