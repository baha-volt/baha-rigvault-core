package cl.bahatech.domain.exception;

public class DuplicateHardwareComponentException extends RuntimeException {
    public DuplicateHardwareComponentException(String message) {
        super(message);
    }
}
