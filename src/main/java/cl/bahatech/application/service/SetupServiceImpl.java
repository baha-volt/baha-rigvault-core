package cl.bahatech.application.service;

import cl.bahatech.domain.entity.Setup;
import cl.bahatech.domain.exception.DuplicateSetupException;
import cl.bahatech.domain.exception.SetupNotFoundException;
import cl.bahatech.domain.repository.SetupRepository;
import java.util.List;

public class SetupServiceImpl implements SetupService {

    private final SetupRepository repository;

    public SetupServiceImpl(SetupRepository repository) {
        this.repository = repository;
    }

    private Setup getExistingSetup(Long id) {
        Setup setup = repository.getSetupById(id);
        if (setup == null) {
            throw new SetupNotFoundException("Setup not found");
        }
        return setup;
    }

    @Override
    public List<Setup> findAll() {
        return repository.getSetups();
    }

    @Override
    public Setup findById(Long id) {
        return getExistingSetup(id);
    }

    @Override
    public Setup save(Setup setup) {

        if (repository.getSetupById(setup.getId()) != null) {
            throw new DuplicateSetupException("Setup already exists");
        }

        return repository.addSetup(setup);
    }

    @Override
    public Setup update(Long id, Setup setup) {
        getExistingSetup(id);

        return repository.updateSetup(id, setup);
    }

    @Override
    public void deleteById(Long id) {
        getExistingSetup(id);
        repository.deleteSetup(id);
    }
}
