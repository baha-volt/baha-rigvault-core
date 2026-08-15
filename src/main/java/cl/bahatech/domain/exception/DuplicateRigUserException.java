package cl.bahatech.domain.exception;

public class DuplicateRigUserException extends RuntimeException{
    public DuplicateRigUserException(String message) {
        super(message);
    }
}
