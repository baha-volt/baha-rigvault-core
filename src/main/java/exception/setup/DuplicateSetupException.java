package exception.setup;

public class DuplicateSetupException extends RuntimeException {
    public DuplicateSetupException(String message) {
        super(message);
    }
}