package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.HardwareComponent;
import cl.bahatech.domain.repository.HardwareComponentRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHardwareComponentRepository implements HardwareComponentRepository {

    private final List<HardwareComponent> components = new ArrayList<>();

    @Override
    public HardwareComponent addComponent(HardwareComponent component) {
        components.add(component);
        return component;
    }

    @Override
    public List<HardwareComponent> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public HardwareComponent getComponentById(Long id) {
        for (HardwareComponent component : components) {
            if (component.getId().equals(id)) {
                return component;
            }
        }
        return null;
    }

    @Override
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

    @Override
    public void deleteComponent(Long id) {
        this.components.removeIf(c -> c.getId().equals(id));
    }
}
