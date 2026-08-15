package cl.bahatech.application.service;

import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.exception.DuplicateRigUserException;
import cl.bahatech.domain.exception.RigUserNotFoundException;
import cl.bahatech.domain.repository.RigUserRepository;

import java.util.List;

public class RigUserServiceImpl implements RigUserService {

    private final RigUserRepository repository;

    public RigUserServiceImpl(RigUserRepository repository) {
        this.repository = repository;
    }

    private RigUser getExistingUser(Long id) {

        RigUser user = repository.getRigUserById(id);

        if (user == null) {
            throw new RigUserNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public List<RigUser> findAll() {
        return repository.getRigUsers();
    }

    @Override
    public RigUser findById(Long id) {
        return getExistingUser(id);
    }

    @Override
    public RigUser save(RigUser rigUser) {

        if (repository.getRigUserById(rigUser.getId()) != null) {
            throw new DuplicateRigUserException("User already exists");
        }

        return repository.addRigUser(rigUser);
    }

    @Override
    public RigUser update(Long id, RigUser rigUser) {
        getExistingUser(id);

        return repository.updateRigUser(id, rigUser);
    }

    @Override
    public void deleteById(Long id) {
        getExistingUser(id);
        repository.deleteRigUser(id);
    }
}
