package cl.bahatech.application.service;

import cl.bahatech.domain.entity.HardwareComponent;
import cl.bahatech.domain.exception.DuplicateHardwareComponentException;
import cl.bahatech.domain.exception.HardwareComponentNotFoundException;
import cl.bahatech.domain.repository.HardwareComponentRepository;
import java.util.List;

public class HardwareComponentServiceImpl implements HardwareComponentService {

    private final HardwareComponentRepository repository;

    public HardwareComponentServiceImpl(HardwareComponentRepository repository) {
        this.repository = repository;
    }

    private HardwareComponent getExistingComponent(Long id) {
        HardwareComponent component = repository.getComponentById(id);
        if (component == null) {
            throw new HardwareComponentNotFoundException("Hardware component not found");
        }
        return component;
    }

    @Override
    public List<HardwareComponent> findAll() {
        return repository.getComponents();
    }

    @Override
    public HardwareComponent findById(Long id) {
        return getExistingComponent(id);
    }

    @Override
    public HardwareComponent save(HardwareComponent component) {

        if (repository.getComponentById(component.getId()) != null) {
            throw new DuplicateHardwareComponentException("Hardware component already exists");
        }

        return repository.addComponent(component);
    }

    @Override
    public HardwareComponent update(Long id, HardwareComponent component) {
        getExistingComponent(id);

        return repository.updateComponent(id, component);
    }

    @Override
    public void deleteById(Long id) {
        getExistingComponent(id);
        repository.deleteComponent(id);
    }
}
