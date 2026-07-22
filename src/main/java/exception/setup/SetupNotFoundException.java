package exception.setup;

public class SetupNotFoundException extends RuntimeException {
    public SetupNotFoundException(String message) {
        super(message);
    }
}