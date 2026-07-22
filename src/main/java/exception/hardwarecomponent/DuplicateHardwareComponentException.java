package exception.hardwarecomponent;

public class DuplicateHardwareComponentException extends RuntimeException {
    public DuplicateHardwareComponentException(String message) {
        super(message);
    }
}