package cl.bahatech.domain.exception;

public class InvalidHardwareComponentQuantityException extends RuntimeException {
    public InvalidHardwareComponentQuantityException(String message) {
        super(message);
    }
}
