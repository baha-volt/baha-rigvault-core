package cl.bahatech.domain.valueobject;

import cl.bahatech.domain.exception.InvalidRigUserEmailException;

public record Email(String value) {
    public Email {
        validateEmail(value);
    }

    private void validateEmail(String email) {
        if (null == email || email.isBlank()) {
            throw new InvalidRigUserEmailException("Invalid email");
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(regex)) {
            throw new InvalidRigUserEmailException("Invalid e-mail format");
        }
    }
}
