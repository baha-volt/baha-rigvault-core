package exception.setup;

public class InvalidSetupUserException extends RuntimeException {
    public InvalidSetupUserException(String message) {
        super(message);
    }
}