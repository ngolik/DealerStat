package by.golik.dealerstat.exception;

/**
 * @author Nikita Golik
 */
public class ResourceAlreadyExistException extends Exception {
    private String message;

    public ResourceAlreadyExistException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
