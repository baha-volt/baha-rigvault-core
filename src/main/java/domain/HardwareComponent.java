package domain;

public class HardwareComponent {
    private Long id;
    private String type;
    private String brand;
    private String model;
    private Integer quantity;
    private Setup setup;

    public HardwareComponent() {
    }

    public HardwareComponent(Long id, String type, String brand, String model, Integer quantity, Setup setup) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }
}
