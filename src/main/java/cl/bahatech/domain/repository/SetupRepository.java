package cl.bahatech.domain.repository;

import cl.bahatech.domain.entity.Setup;

import java.util.List;

public interface SetupRepository {
    Setup addSetup(Setup setup);
    List<Setup> getSetups();
    Setup getSetupById(Long id);
    Setup updateSetup(Long id, Setup updatedSetup);
    void deleteSetup(Long id);
}
