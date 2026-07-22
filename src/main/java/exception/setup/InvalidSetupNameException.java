package exception.setup;

public class InvalidSetupNameException extends RuntimeException {
    public InvalidSetupNameException(String message) {
        super(message);
    }
}