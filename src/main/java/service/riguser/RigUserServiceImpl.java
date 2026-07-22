package service.riguser;

import domain.RigUser;
import exception.riguser.DuplicateRigUserException;
import exception.riguser.RigUserNotFoundException;
import repository.RigUserRepository;

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
        rigUser.validateEmail(rigUser.getEmail());
        rigUser.validateName(rigUser.getName());
        rigUser.validatePassword(rigUser.getPassword());

        if (repository.getRigUserById(rigUser.getId()) != null) {
            throw new DuplicateRigUserException("User already exists");
        }

        return repository.addRigUser(rigUser);
    }

    @Override
    public RigUser update(Long id, RigUser rigUser) {
        rigUser.validateEmail(rigUser.getEmail());
        rigUser.validateName(rigUser.getName());
        getExistingUser(id);

        return repository.updateRigUser(id, rigUser);
    }

    @Override
    public void deleteById(Long id) {
        getExistingUser(id);
        repository.deleteRigUser(id);
    }
}
