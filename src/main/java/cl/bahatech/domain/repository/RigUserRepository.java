package cl.bahatech.domain.repository;

import cl.bahatech.domain.entity.RigUser;

import java.util.List;

public interface RigUserRepository {
    RigUser addRigUser(RigUser user);
    List<RigUser> getRigUsers();
    RigUser getRigUserById(Long id);
    RigUser updateRigUser(Long id, RigUser user);
    void deleteRigUser(Long id);
}
