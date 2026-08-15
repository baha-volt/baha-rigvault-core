package cl.bahatech.domain.entity;

import cl.bahatech.domain.exception.InvalidSetupDescriptionException;
import cl.bahatech.domain.exception.InvalidSetupNameException;
import cl.bahatech.domain.exception.InvalidSetupUserException;

public class Setup {
    private Long id;
    private String name;
    private String description;
    private RigUser rigUser;

    public Setup() {
    }

    public Setup(Long id, String name, String description, RigUser rigUser) {
        validateName(name);
        validateDescription(description);
        validateUser(rigUser);

        this.id = id;
        this.name = name;
        this.description = description;
        this.rigUser = rigUser;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public RigUser getUser() {
        return rigUser;
    }

    public void setUser(RigUser rigUser) {
        validateUser(rigUser);
        this.rigUser = rigUser;
    }

    public void validateName(String name) {
        if (null == name || name.isBlank()) {
            throw new InvalidSetupNameException("Invalid setup name");
        }
        if (name.length() < 3 || name.length() > 50) {
            throw new InvalidSetupNameException("Setup name must contain between 3 and 50 characters");
        }
    }

    public void validateDescription(String description) {
        if (null == description || description.isBlank()) {
            throw new InvalidSetupDescriptionException("Invalid setup description");
        }
        if (description.length() > 255) {
            throw new InvalidSetupDescriptionException("Setup description cannot exceed 255 characters");
        }
    }

    public void validateUser(RigUser rigUser) {
        if (null == rigUser) {
            throw new InvalidSetupUserException("Setup must have a valid user assigned");
        }
    }
}
