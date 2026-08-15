package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.RigUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cl.bahatech.domain.repository.RigUserRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestRigUserRepository {

    private RigUserRepository repository;
    private RigUser testSubject;
    private RigUser secondaryUser;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRigUserRepository();

        testSubject = new RigUser(1L, "Alejandro Silva", "ale.silva@email.com", "pass123456");
        secondaryUser = new RigUser(2L, "Beatriz Gómez", "beatriz.g@email.com", "secureB@2026");
    }

    @Test
    void shouldAddRigUserSuccessfully() {

        RigUser savedUser = repository.addRigUser(testSubject);

        assertNotNull(savedUser);
        assertEquals(testSubject.getId(), savedUser.getId());

        List<RigUser> allUsers = repository.getRigUsers();
        assertEquals(1, allUsers.size());
        assertTrue(allUsers.contains(testSubject));
    }

    @Test
    void shouldReturnAllRigUsers() {

        repository.addRigUser(testSubject);
        repository.addRigUser(secondaryUser);

        List<RigUser> result = repository.getRigUsers();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(testSubject)),
                () -> assertTrue(result.contains(secondaryUser))
        );
    }

    @Test
    void shouldReturnEncapsulatedListToPreventExternalModification() {

        repository.addRigUser(testSubject);
        List<RigUser> initialList = repository.getRigUsers();

        initialList.clear();

        List<RigUser> repositoryList = repository.getRigUsers();
        assertEquals(1, repositoryList.size());
    }

    @Test
    void shouldFindRigUserByIdSuccessfully() {

        repository.addRigUser(testSubject);
        repository.addRigUser(secondaryUser);

        RigUser foundUser = repository.getRigUserById(1L);

        assertNotNull(foundUser);
        assertEquals("Alejandro Silva", foundUser.getName());
    }

    @Test
    void shouldReturnNullWhenUserByIdDoesNotExist() {

        repository.addRigUser(testSubject);

        RigUser foundUser = repository.getRigUserById(99L);

        assertNull(foundUser);
    }

    @Test
    void shouldUpdateRigUserSuccessfully() {

        repository.addRigUser(testSubject);
        RigUser updatedData = new RigUser(1L, "Alejandro Modificado", "ale.mod@email.com", "nuevaPassword");

        RigUser result = repository.updateRigUser(1L, updatedData);

        assertNotNull(result);
        assertEquals("Alejandro Modificado", result.getName());
        assertEquals("ale.mod@email.com", result.getEmail().value());

        RigUser persistenceCheck = repository.getRigUserById(1L);
        assertEquals("Alejandro Modificado", persistenceCheck.getName());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingUser() {

        repository.addRigUser(testSubject);
        RigUser updatedData = new RigUser(99L, "Fantasma", "ghost@email.com", "ghostPass1");

        RigUser result = repository.updateRigUser(99L, updatedData);

        assertNull(result);
    }

    @Test
    void shouldDeleteRigUserSuccessfully() {

        repository.addRigUser(testSubject);
        repository.addRigUser(secondaryUser);

        repository.deleteRigUser(1L);

        List<RigUser> allUsers = repository.getRigUsers();
        assertEquals(1, allUsers.size());
        assertNull(repository.getRigUserById(1L));
        assertNotNull(repository.getRigUserById(2L));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistingUser() {

        repository.addRigUser(testSubject);

        assertDoesNotThrow(() -> repository.deleteRigUser(99L));
        assertEquals(1, repository.getRigUsers().size());
    }
}
