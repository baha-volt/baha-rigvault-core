package exception.hardwarecomponent;

public class InvalidHardwareComponentModelException extends RuntimeException {
    public InvalidHardwareComponentModelException(String message) {
        super(message);
    }
}