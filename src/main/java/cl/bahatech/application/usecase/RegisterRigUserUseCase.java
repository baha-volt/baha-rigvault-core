package cl.bahatech.application.usecase;

import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.repository.RigUserRepository;
import cl.bahatech.domain.exception.DuplicateRigUserException;
import cl.bahatech.application.port.MessageNotifier;

public class RegisterRigUserUseCase {
    private final RigUserRepository userRepository;
    private final MessageNotifier messageNotifier;

    public RegisterRigUserUseCase(RigUserRepository userRepository, MessageNotifier messageNotifier) {
        this.userRepository = userRepository;
        this.messageNotifier = messageNotifier;
    }

    public RigUser execute(RigUser user) {
        if (userRepository.getRigUserById(user.getId()) != null) {
            throw new DuplicateRigUserException("RigUser already exists");
        }
        RigUser savedUser = userRepository.addRigUser(user);
        messageNotifier.notify("User registered successfully: " + savedUser.getName());
        return savedUser;
    }
}
