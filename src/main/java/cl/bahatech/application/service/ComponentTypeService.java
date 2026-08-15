package cl.bahatech.application.service;

import cl.bahatech.domain.entity.ComponentType;

import java.util.List;

public interface ComponentTypeService {
    List<ComponentType> findAll();
    ComponentType findById(Long id);
    ComponentType save(ComponentType componentType);
    ComponentType update(Long id, ComponentType componentType);
    void deleteById(Long id);
}
