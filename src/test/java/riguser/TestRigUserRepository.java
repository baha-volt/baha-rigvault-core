package riguser;

import domain.RigUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RigUserRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestRigUserRepository {

    private RigUserRepository repository;
    private RigUser testSubject;
    private RigUser secondaryUser;

    @BeforeEach
    void setUp() {
        repository = new RigUserRepository();

        testSubject = new RigUser(1L, "Alejandro Silva", "ale.silva@email.com", "pass1234");
        secondaryUser = new RigUser(2L, "Beatriz Gómez", "beatriz.g@email.com", "secureB@2026");
    }


    @Test
    void shouldAddRigUserSuccessfully() {
        // Act
        RigUser savedUser = repository.addRigUser(testSubject);

        // Assert
        assertNotNull(savedUser);
        assertEquals(testSubject.getId(), savedUser.getId());

        List<RigUser> allUsers = repository.getRigUsers();
        assertEquals(1, allUsers.size());
        assertTrue(allUsers.contains(testSubject));
    }

    @Test
    void shouldReturnAllRigUsers() {
        // Arrange
        repository.addRigUser(testSubject);
        repository.addRigUser(secondaryUser);

        // Act
        List<RigUser> result = repository.getRigUsers();

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(testSubject)),
                () -> assertTrue(result.contains(secondaryUser))
        );
    }

    @Test
    void shouldReturnEncapsulatedListToPreventExternalModification() {
        // Arrange
        repository.addRigUser(testSubject);
        List<RigUser> initialList = repository.getRigUsers();

        // Act
        initialList.clear();

        // Assert
        List<RigUser> repositoryList = repository.getRigUsers();
        assertEquals(1, repositoryList.size());
    }

    @Test
    void shouldFindRigUserByIdSuccessfully() {
        // Arrange
        repository.addRigUser(testSubject);
        repository.addRigUser(secondaryUser);

        // Act
        RigUser foundUser = repository.getRigUserById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals("Alejandro Silva", foundUser.getName());
    }

    @Test
    void shouldReturnNullWhenUserByIdDoesNotExist() {
        // Arrange
        repository.addRigUser(testSubject);

        // Act
        RigUser foundUser = repository.getRigUserById(99L);

        // Assert
        assertNull(foundUser);
    }


    @Test
    void shouldUpdateRigUserSuccessfully() {
        // Arrange
        repository.addRigUser(testSubject);
        RigUser updatedData = new RigUser(1L, "Alejandro Modificado", "ale.mod@email.com", "nuevaPass");

        // Act
        RigUser result = repository.updateRigUser(1L, updatedData);

        // Assert
        assertNotNull(result);
        assertEquals("Alejandro Modificado", result.getName());
        assertEquals("ale.mod@email.com", result.getEmail());

        // Persistance check
        RigUser persistenceCheck = repository.getRigUserById(1L);
        assertEquals("Alejandro Modificado", persistenceCheck.getName());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingUser() {
        // Arrange
        repository.addRigUser(testSubject);
        RigUser updatedData = new RigUser(99L, "Fantasma", "ghost@email.com", "pass");

        // Act
        RigUser result = repository.updateRigUser(99L, updatedData);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldDeleteRigUserSuccessfully() {
        // Arrange
        repository.addRigUser(testSubject);
        repository.addRigUser(secondaryUser);

        // Act
        repository.deleteRigUser(1L);

        // Assert
        List<RigUser> allUsers = repository.getRigUsers();
        assertEquals(1, allUsers.size());
        assertNull(repository.getRigUserById(1L));
        assertNotNull(repository.getRigUserById(2L));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistingUser() {
        // Arrange
        repository.addRigUser(testSubject);

        // Act and Assert
        assertDoesNotThrow(() -> repository.deleteRigUser(99L));
        assertEquals(1, repository.getRigUsers().size());
    }
}