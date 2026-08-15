package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.Setup;
import cl.bahatech.domain.repository.SetupRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemorySetupRepository implements SetupRepository {

    private final List<Setup> setups = new ArrayList<>();

    @Override
    public Setup addSetup(Setup setup) {
        setups.add(setup);
        return setup;
    }

    @Override
    public List<Setup> getSetups() {
        return new ArrayList<>(setups);
    }

    @Override
    public Setup getSetupById(Long id) {
        for (Setup setup : setups) {
            if (setup.getId().equals(id)) {
                return setup;
            }
        }
        return null;
    }

    @Override
    public Setup updateSetup(Long id, Setup updatedSetup) {
        for (Setup setup : setups) {
            if (setup.getId().equals(id)) {
                setup.setName(updatedSetup.getName());
                setup.setDescription(updatedSetup.getDescription());
                setup.setUser(updatedSetup.getUser());
                return setup;
            }
        }
        return null;
    }

    @Override
    public void deleteSetup(Long id) {
        this.setups.removeIf(s -> s.getId().equals(id));
    }
}
