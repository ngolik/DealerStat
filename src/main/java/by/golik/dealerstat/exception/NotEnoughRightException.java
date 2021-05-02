package by.golik.dealerstat.exception;

/**
 * @author Nikita Golik
 */
public class NotEnoughRightException extends Exception {
    private String message;

    public NotEnoughRightException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
