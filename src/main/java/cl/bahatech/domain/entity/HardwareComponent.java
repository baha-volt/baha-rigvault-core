package cl.bahatech.domain.entity;

import cl.bahatech.domain.exception.InvalidHardwareComponentBrandException;
import cl.bahatech.domain.exception.InvalidHardwareComponentModelException;
import cl.bahatech.domain.exception.InvalidHardwareComponentQuantityException;
import cl.bahatech.domain.exception.InvalidHardwareComponentSetupException;
import cl.bahatech.domain.exception.InvalidHardwareComponentTypeException;

public class HardwareComponent {
    private Long id;
    private ComponentType type;
    private String brand;
    private String model;
    private Integer quantity;
    private Setup setup;

    public HardwareComponent() {
    }

    public HardwareComponent(Long id, ComponentType type, String brand, String model, Integer quantity, Setup setup) {
        validateType(type);
        validateBrand(brand);
        validateModel(model);
        validateQuantity(quantity);
        validateSetup(setup);

        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.quantity = quantity;
        this.setup = setup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        validateType(type);
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        validateBrand(brand);
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        validateModel(model);
        this.model = model;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        validateSetup(setup);
        this.setup = setup;
    }

    public void validateType(ComponentType type) {
        if (null == type) {
            throw new InvalidHardwareComponentTypeException("Component must be assigned to a valid type");
        }
    }

    public void validateBrand(String brand) {
        if (null == brand || brand.isBlank()) {
            throw new InvalidHardwareComponentBrandException("Invalid component brand");
        }
    }

    public void validateModel(String model) {
        if (null == model || model.isBlank()) {
            throw new InvalidHardwareComponentModelException("Invalid component model");
        }
    }

    public void validateQuantity(Integer quantity) {
        if (null == quantity) {
            throw new InvalidHardwareComponentQuantityException("Quantity cannot be null");
        }
        if (quantity <= 0) {
            throw new InvalidHardwareComponentQuantityException("Quantity must be greater than zero");
        }
    }

    public void validateSetup(Setup setup) {
        if (null == setup) {
            throw new InvalidHardwareComponentSetupException("Component must be assigned to a valid setup");
        }
    }
}
