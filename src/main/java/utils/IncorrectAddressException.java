package utils;

/**
 * Thrown to indicate that a provided address value is invalid.
 */
public class IncorrectAddressException extends Exception {
    public IncorrectAddressException(String message) {
        super(message);
    }
}
