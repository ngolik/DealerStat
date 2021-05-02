package by.golik.dealerstat.exception;

/**
 * @author Nikita Golik
 */
public class ResourceNotFoundException extends Exception {
    private String message;

    public ResourceNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
