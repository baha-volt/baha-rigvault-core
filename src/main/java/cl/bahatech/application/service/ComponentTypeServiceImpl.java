package cl.bahatech.application.service;

import cl.bahatech.domain.entity.ComponentType;
import cl.bahatech.domain.exception.ComponentTypeNotFoundException;
import cl.bahatech.domain.exception.DuplicateComponentTypeException;
import cl.bahatech.domain.repository.ComponentTypeRepository;

import java.util.List;

public class ComponentTypeServiceImpl implements ComponentTypeService {

    private final ComponentTypeRepository repository;

    public ComponentTypeServiceImpl(ComponentTypeRepository repository) {
        this.repository = repository;
    }

    private ComponentType getExistingComponentType(Long id) {
        ComponentType componentType = repository.getComponentTypeById(id);
        if (componentType == null) {
            throw new ComponentTypeNotFoundException("Component type not found");
        }
        return componentType;
    }

    @Override
    public List<ComponentType> findAll() {
        return repository.getComponentTypes();
    }

    @Override
    public ComponentType findById(Long id) {
        return getExistingComponentType(id);
    }

    @Override
    public ComponentType save(ComponentType componentType) {

        if (repository.getComponentTypeByName(componentType.getName()) != null) {
            throw new DuplicateComponentTypeException("Component type already exists");
        }

        return repository.addComponentType(componentType);
    }

    @Override
    public ComponentType update(Long id, ComponentType componentType) {
        getExistingComponentType(id);
        return repository.updateComponentType(id, componentType);
    }

    @Override
    public void deleteById(Long id) {
        getExistingComponentType(id);
        repository.deleteComponentType(id);
    }
}
