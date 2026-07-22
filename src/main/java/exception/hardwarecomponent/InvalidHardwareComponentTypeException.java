package exception.hardwarecomponent;

public class InvalidHardwareComponentTypeException extends RuntimeException {
    public InvalidHardwareComponentTypeException(String message) {
        super(message);
    }
}