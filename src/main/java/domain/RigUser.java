package domain;

import exception.riguser.InvalidRigUserEmailException;
import exception.riguser.InvalidRigUserNameException;
import exception.riguser.InvalidRigUserPasswordException;

public class RigUser {
    private Long id;
    private String name;
    private String email;
    private String password;

    public RigUser() {
    }

    public RigUser(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
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
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Custom methods
    public void validateName(String name) {
        if (null == name || name.isBlank()) {
            throw new InvalidRigUserNameException("Invalid name");
        }
        if (name.length() < 3 || name.length() > 100) {
            throw new InvalidRigUserNameException("Name must contain between 3 and 100 characters");
        }
    }

    public void validateEmail(String email) {
        if (null == email || email.isBlank()) {
            throw new InvalidRigUserEmailException("Invalid email");
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(regex)) {
            throw new InvalidRigUserEmailException("Invalid e-mail format");
        }
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