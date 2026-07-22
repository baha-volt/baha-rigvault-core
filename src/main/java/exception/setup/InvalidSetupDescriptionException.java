package exception.setup;

public class InvalidSetupDescriptionException extends RuntimeException {
    public InvalidSetupDescriptionException(String message) {
        super(message);
    }
}