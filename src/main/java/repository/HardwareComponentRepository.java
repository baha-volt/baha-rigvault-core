package repository;

import domain.HardwareComponent;
import java.util.ArrayList;
import java.util.List;

public class HardwareComponentRepository {

    private final List<HardwareComponent> components = new ArrayList<>();

    public HardwareComponent addComponent(HardwareComponent component) {
        components.add(component);
        return component;
    }

    public List<HardwareComponent> getComponents() {
        return new ArrayList<>(components);
    }

    public HardwareComponent getComponentById(Long id) {
        for (HardwareComponent component : components) {
            if (component.getId().equals(id)) {
                return component;
            }
        }
        return null;
    }

    public HardwareComponent updateComponent(Long id, HardwareComponent updatedComponent) {
        for (HardwareComponent component : components) {
            if (component.getId().equals(id)) {
                component.setType(updatedComponent.getType());
                component.setBrand(updatedComponent.getBrand());
                component.setModel(updatedComponent.getModel());
                component.setQuantity(updatedComponent.getQuantity());
                component.setSetup(updatedComponent.getSetup());
                return component;
            }
        }
        return null;
    }

    public void deleteComponent(Long id) {
        this.components.removeIf(c -> c.getId().equals(id));
    }
}