package cl.bahatech.domain.entity;

import cl.bahatech.domain.exception.InvalidRigUserNameException;
import cl.bahatech.domain.exception.InvalidRigUserPasswordException;
import cl.bahatech.domain.valueobject.Email;

public class RigUser {
    private Long id;
    private String name;
    private Email email;
    private String password;

    public RigUser() {
    }

    public RigUser(Long id, String name, Email email, String password) {
        validateName(name);
        validatePassword(password);

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public RigUser(Long id, String name, String email, String password) {
        validateName(name);
        validatePassword(password);

        this.id = id;
        this.name = name;
        this.email = new Email(email);
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void validateName(String name) {
        if (null == name || name.isBlank()) {
            throw new InvalidRigUserNameException("Invalid name");
        }
        if (name.length() < 3 || name.length() > 100) {
            throw new InvalidRigUserNameException("Name must contain between 3 and 100 characters");
        }
    }

    public void validateEmail(String email) {
        new Email(email);
    }

    public void validatePassword(String password) {
        if (null == password || password.isBlank()) {
            throw new InvalidRigUserPasswordException("Invalid password");
        }
        if (password.length() < 10) {
            throw new InvalidRigUserPasswordException("Password must contain at least 10 characters");
        }
    }
}
