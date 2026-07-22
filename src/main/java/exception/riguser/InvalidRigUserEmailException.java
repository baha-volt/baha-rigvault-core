package exception.riguser;

public class InvalidRigUserEmailException extends RuntimeException {
    public InvalidRigUserEmailException(String message) {
        super(message);
    }
}
