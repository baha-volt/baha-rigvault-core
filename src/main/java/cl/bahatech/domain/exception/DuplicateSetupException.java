package cl.bahatech.domain.exception;

public class DuplicateSetupException extends RuntimeException {
    public DuplicateSetupException(String message) {
        super(message);
    }
}
