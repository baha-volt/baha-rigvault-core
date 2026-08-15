package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.repository.RigUserRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRigUserRepository implements RigUserRepository {

    private final List<RigUser> rigUsers = new ArrayList<>();

    @Override
    public RigUser addRigUser(RigUser user) {
        rigUsers.add(user);
        return user;
    }

    @Override
    public List<RigUser> getRigUsers() {
        return new ArrayList<>(rigUsers);
    }

    @Override
    public RigUser getRigUserById(Long id) {
        for (RigUser rigUser : rigUsers) {
            if (rigUser.getId().equals(id)) {
                return rigUser;
            }
        }
        return null;
    }

    @Override
    public RigUser updateRigUser(Long id, RigUser user) {
        for (RigUser rigUser : rigUsers) {
            if (rigUser.getId().equals(id)) {
                rigUser.setName(user.getName());
                rigUser.setEmail(user.getEmail());
                return rigUser;
            }
        }
        return null;
    }

    @Override
    public void deleteRigUser(Long id) {
        this.rigUsers.removeIf(r -> r.getId().equals(id));
    }
}
