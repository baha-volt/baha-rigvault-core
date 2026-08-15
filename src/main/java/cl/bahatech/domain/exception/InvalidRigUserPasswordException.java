package cl.bahatech.domain.exception;

public class InvalidRigUserPasswordException extends RuntimeException {
    public InvalidRigUserPasswordException(String message) {
        super(message);
    }
}
