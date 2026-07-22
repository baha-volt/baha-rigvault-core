package repository;

import domain.RigUser;
import java.util.ArrayList;
import java.util.List;

public class RigUserRepository {

    private final List<RigUser> rigUsers = new ArrayList<>();

    public RigUser addRigUser(RigUser user) {
        rigUsers.add(user);
        return user;
    }

    public List<RigUser> getRigUsers() {
        return new ArrayList<>(rigUsers);
    }

    public RigUser getRigUserById(Long id) {

        for (RigUser rigUser : rigUsers) {

            if (rigUser.getId().equals(id)) {
                return rigUser;
            }

        }

        return null;
    }

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

    public void deleteRigUser(Long id) {
        this.rigUsers.removeIf(r -> r.getId().equals(id));
    }
}
