package cl.bahatech.domain.repository;

import cl.bahatech.domain.entity.HardwareComponent;

import java.util.List;

public interface HardwareComponentRepository {
    HardwareComponent addComponent(HardwareComponent component);
    List<HardwareComponent> getComponents();
    HardwareComponent getComponentById(Long id);
    HardwareComponent updateComponent(Long id, HardwareComponent updatedComponent);
    void deleteComponent(Long id);
}
