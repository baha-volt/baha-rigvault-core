package cl.bahatech.domain.entity;

import cl.bahatech.domain.exception.InvalidComponentTypeDescriptionException;
import cl.bahatech.domain.exception.InvalidComponentTypeNameException;

public class ComponentType {
    private Long id;
    private String name;
    private String description;

    public ComponentType() {
    }

    public ComponentType(Long id, String name, String description) {
        validateName(name);
        validateDescription(description);

        this.id = id;
        this.name = name;
        this.description = description;
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

    public void validateName(String name) {
        if (null == name || name.isBlank()) {
            throw new InvalidComponentTypeNameException("Invalid component type name");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new InvalidComponentTypeNameException("Component type name must contain between 2 and 50 characters");
        }
    }

    public void validateDescription(String description) {
        if (null == description || description.isBlank()) {
            throw new InvalidComponentTypeDescriptionException("Invalid component type description");
        }
        if (description.length() > 255) {
            throw new InvalidComponentTypeDescriptionException("Component type description cannot exceed 255 characters");
        }
    }
}
