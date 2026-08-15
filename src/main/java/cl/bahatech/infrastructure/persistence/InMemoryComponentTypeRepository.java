package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.ComponentType;
import cl.bahatech.domain.repository.ComponentTypeRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryComponentTypeRepository implements ComponentTypeRepository {

    private final List<ComponentType> componentTypes = new ArrayList<>();

    @Override
    public ComponentType addComponentType(ComponentType componentType) {
        componentTypes.add(componentType);
        return componentType;
    }

    @Override
    public List<ComponentType> getComponentTypes() {
        return new ArrayList<>(componentTypes);
    }

    @Override
    public ComponentType getComponentTypeById(Long id) {
        for (ComponentType componentType : componentTypes) {
            if (componentType.getId().equals(id)) {
                return componentType;
            }
        }
        return null;
    }

    @Override
    public ComponentType getComponentTypeByName(String name) {
        for (ComponentType componentType : componentTypes) {
            if (componentType.getName().equalsIgnoreCase(name)) {
                return componentType;
            }
        }
        return null;
    }

    @Override
    public ComponentType updateComponentType(Long id, ComponentType updatedComponentType) {
        for (ComponentType componentType : componentTypes) {
            if (componentType.getId().equals(id)) {
                componentType.setName(updatedComponentType.getName());
                componentType.setDescription(updatedComponentType.getDescription());
                return componentType;
            }
        }
        return null;
    }

    @Override
    public void deleteComponentType(Long id) {
        this.componentTypes.removeIf(c -> c.getId().equals(id));
    }
}
