package cl.bahatech.domain.exception;

public class ComponentTypeNotFoundException extends RuntimeException {
    public ComponentTypeNotFoundException(String message) {
        super(message);
    }
}
