package utils;

/**
 * Thrown to indicate that a provided product price is invalid.
 */
public class IncorrectProductPriceException extends Exception {
    public IncorrectProductPriceException(String message) {
        super(message);
    }
}
