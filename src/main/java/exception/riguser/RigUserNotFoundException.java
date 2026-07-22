package exception.riguser;

public class RigUserNotFoundException extends RuntimeException {
    public RigUserNotFoundException(String message) {
        super(message);
    }
}
