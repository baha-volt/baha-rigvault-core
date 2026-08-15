package cl.bahatech.domain.exception;

public class HardwareComponentNotFoundException extends RuntimeException {
    public HardwareComponentNotFoundException(String message) {
        super(message);
    }
}
