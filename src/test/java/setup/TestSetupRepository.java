package setup;

import domain.RigUser;
import domain.Setup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.SetupRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestSetupRepository {

    private SetupRepository repository;
    private Setup testSubject;
    private Setup secondarySetup;
    private RigUser mockUser;

    @BeforeEach
    void setUp() {
        repository = new SetupRepository();
        mockUser = new RigUser(1L, "Alejandro", "ale@test.com", "pass123456");

        testSubject = new Setup(1L, "Streamer Build", "Dual PC setup", mockUser);
        secondarySetup = new Setup(2L, "Render Farm", "Threadripper build", mockUser);
    }

    @Test
    void shouldAddSetupSuccessfully() {
        // Act
        Setup savedSetup = repository.addSetup(testSubject);

        // Assert
        assertNotNull(savedSetup);
        assertEquals(testSubject.getId(), savedSetup.getId());

        List<Setup> allSetups = repository.getSetups();
        assertEquals(1, allSetups.size());
        assertTrue(allSetups.contains(testSubject));
    }

    @Test
    void shouldReturnAllSetups() {
        // Arrange
        repository.addSetup(testSubject);
        repository.addSetup(secondarySetup);

        // Act
        List<Setup> result = repository.getSetups();

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(testSubject)),
                () -> assertTrue(result.contains(secondarySetup))
        );
    }

    @Test
    void shouldReturnEncapsulatedListToPreventExternalModification() {
        // Arrange
        repository.addSetup(testSubject);
        List<Setup> initialList = repository.getSetups();

        // Act
        initialList.clear();

        // Assert
        List<Setup> repositoryList = repository.getSetups();
        assertEquals(1, repositoryList.size());
    }

    @Test
    void shouldFindSetupByIdSuccessfully() {
        // Arrange
        repository.addSetup(testSubject);
        repository.addSetup(secondarySetup);

        // Act
        Setup foundSetup = repository.getSetupById(1L);

        // Assert
        assertNotNull(foundSetup);
        assertEquals("Streamer Build", foundSetup.getName());
    }

    @Test
    void shouldReturnNullWhenSetupByIdDoesNotExist() {
        // Arrange
        repository.addSetup(testSubject);

        // Act
        Setup foundSetup = repository.getSetupById(99L);

        // Assert
        assertNull(foundSetup);
    }

    @Test
    void shouldUpdateSetupSuccessfully() {
        // Arrange
        repository.addSetup(testSubject);
        Setup updatedData = new Setup(1L, "Streamer Build V2", "Updated GPU", mockUser);

        // Act
        Setup result = repository.updateSetup(1L, updatedData);

        // Assert
        assertNotNull(result);
        assertEquals("Streamer Build V2", result.getName());
        assertEquals("Updated GPU", result.getDescription());

        Setup persistenceCheck = repository.getSetupById(1L);
        assertEquals("Streamer Build V2", persistenceCheck.getName());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingSetup() {
        // Arrange
        repository.addSetup(testSubject);
        Setup updatedData = new Setup(99L, "Ghost Setup", "Does not exist", mockUser);

        // Act
        Setup result = repository.updateSetup(99L, updatedData);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldDeleteSetupSuccessfully() {
        // Arrange
        repository.addSetup(testSubject);
        repository.addSetup(secondarySetup);

        // Act
        repository.deleteSetup(1L);

        // Assert
        List<Setup> allSetups = repository.getSetups();
        assertEquals(1, allSetups.size());
        assertNull(repository.getSetupById(1L));
        assertNotNull(repository.getSetupById(2L));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistingSetup() {
        // Arrange
        repository.addSetup(testSubject);

        // Act and Assert
        assertDoesNotThrow(() -> repository.deleteSetup(99L));
        assertEquals(1, repository.getSetups().size());
    }
}