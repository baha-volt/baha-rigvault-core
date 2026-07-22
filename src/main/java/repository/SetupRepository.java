package repository;

import domain.Setup;
import java.util.ArrayList;
import java.util.List;

public class SetupRepository {

    private final List<Setup> setups = new ArrayList<>();

    public Setup addSetup(Setup setup) {
        setups.add(setup);
        return setup;
    }

    public List<Setup> getSetups() {
        return new ArrayList<>(setups);
    }

    public Setup getSetupById(Long id) {
        for (Setup setup : setups) {
            if (setup.getId().equals(id)) {
                return setup;
            }
        }
        return null;
    }

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

    public void deleteSetup(Long id) {
        this.setups.removeIf(s -> s.getId().equals(id));
    }
}