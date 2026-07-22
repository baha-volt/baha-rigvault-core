package exception.hardwarecomponent;

public class InvalidHardwareComponentBrandException extends RuntimeException {
    public InvalidHardwareComponentBrandException(String message) {
        super(message);
    }
}