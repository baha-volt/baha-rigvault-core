package exception.hardwarecomponent;

public class HardwareComponentNotFoundException extends RuntimeException {
    public HardwareComponentNotFoundException(String message) {
        super(message);
    }
}