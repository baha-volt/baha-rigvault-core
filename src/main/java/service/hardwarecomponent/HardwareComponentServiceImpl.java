package service.hardwarecomponent;

import domain.HardwareComponent;
import exception.hardwarecomponent.DuplicateHardwareComponentException;
import exception.hardwarecomponent.HardwareComponentNotFoundException;
import repository.HardwareComponentRepository;
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
        component.validateType(component.getType());
        component.validateBrand(component.getBrand());
        component.validateModel(component.getModel());
        component.validateQuantity(component.getQuantity());
        component.validateSetup(component.getSetup());

        if (repository.getComponentById(component.getId()) != null) {
            throw new DuplicateHardwareComponentException("Hardware component already exists");
        }

        return repository.addComponent(component);
    }

    @Override
    public HardwareComponent update(Long id, HardwareComponent component) {
        component.validateType(component.getType());
        component.validateBrand(component.getBrand());
        component.validateModel(component.getModel());
        component.validateQuantity(component.getQuantity());
        component.validateSetup(component.getSetup());

        getExistingComponent(id);

        return repository.updateComponent(id, component);
    }

    @Override
    public void deleteById(Long id) {
        getExistingComponent(id);
        repository.deleteComponent(id);
    }
}