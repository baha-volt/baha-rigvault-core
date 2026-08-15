package cl.bahatech.domain.repository;

import cl.bahatech.domain.entity.ComponentType;

import java.util.List;

public interface ComponentTypeRepository {
    ComponentType addComponentType(ComponentType componentType);
    List<ComponentType> getComponentTypes();
    ComponentType getComponentTypeById(Long id);
    ComponentType getComponentTypeByName(String name);
    ComponentType updateComponentType(Long id, ComponentType updatedComponentType);
    void deleteComponentType(Long id);
}
