package cl.bahatech.application.service;

import cl.bahatech.domain.entity.HardwareComponent;
import java.util.List;

public interface HardwareComponentService {
    List<HardwareComponent> findAll();
    HardwareComponent findById(Long id);
    HardwareComponent save(HardwareComponent component);
    HardwareComponent update(Long id, HardwareComponent component);
    void deleteById(Long id);
}
