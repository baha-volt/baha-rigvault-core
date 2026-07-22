package exception.hardwarecomponent;

public class InvalidHardwareComponentSetupException extends RuntimeException {
    public InvalidHardwareComponentSetupException(String message) {
        super(message);
    }
}