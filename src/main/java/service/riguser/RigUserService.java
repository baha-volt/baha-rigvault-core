package service.riguser;

import domain.RigUser;
import java.util.List;

public interface RigUserService {
    List<RigUser> findAll();
    RigUser findById(Long id);
    RigUser save(RigUser rigUser);
    RigUser update(Long id, RigUser rigUser);
    void deleteById(Long id);
}
