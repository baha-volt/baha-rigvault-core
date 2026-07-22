package exception.riguser;

public class DuplicateRigUserException extends RuntimeException{
    public DuplicateRigUserException(String message) {
        super(message);
    }
}
