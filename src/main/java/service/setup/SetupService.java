package service.setup;

import domain.Setup;
import java.util.List;

public interface SetupService {
    List<Setup> findAll();
    Setup findById(Long id);
    Setup save(Setup setup);
    Setup update(Long id, Setup setup);
    void deleteById(Long id);
}