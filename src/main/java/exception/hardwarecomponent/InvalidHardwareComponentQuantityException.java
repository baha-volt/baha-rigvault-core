package exception.hardwarecomponent;

public class InvalidHardwareComponentQuantityException extends RuntimeException {
    public InvalidHardwareComponentQuantityException(String message) {
        super(message);
    }
}