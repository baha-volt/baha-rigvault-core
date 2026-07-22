package exception.riguser;

public class InvalidRigUserPasswordException extends RuntimeException {
    public InvalidRigUserPasswordException(String message) {
        super(message);
    }
}
