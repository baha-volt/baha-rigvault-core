package exception.riguser;

public class InvalidRigUserNameException extends RuntimeException {
    public InvalidRigUserNameException(String message) {
        super(message);
    }
}
