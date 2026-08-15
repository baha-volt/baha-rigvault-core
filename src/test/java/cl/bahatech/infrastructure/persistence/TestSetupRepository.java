package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.entity.Setup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cl.bahatech.domain.repository.SetupRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestSetupRepository {

    private SetupRepository repository;
    private Setup testSubject;
    private Setup secondarySetup;
    private RigUser mockUser;

    @BeforeEach
    void setUp() {
        repository = new InMemorySetupRepository();
        mockUser = new RigUser(1L, "Alejandro", "ale@test.com", "pass123456");

        testSubject = new Setup(1L, "Streamer Build", "Dual PC setup", mockUser);
        secondarySetup = new Setup(2L, "Render Farm", "Threadripper build", mockUser);
    }

    @Test
    void shouldAddSetupSuccessfully() {

        Setup savedSetup = repository.addSetup(testSubject);

        assertNotNull(savedSetup);
        assertEquals(testSubject.getId(), savedSetup.getId());

        List<Setup> allSetups = repository.getSetups();
        assertEquals(1, allSetups.size());
        assertTrue(allSetups.contains(testSubject));
    }

    @Test
    void shouldReturnAllSetups() {

        repository.addSetup(testSubject);
        repository.addSetup(secondarySetup);

        List<Setup> result = repository.getSetups();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(testSubject)),
                () -> assertTrue(result.contains(secondarySetup))
        );
    }

    @Test
    void shouldReturnEncapsulatedListToPreventExternalModification() {

        repository.addSetup(testSubject);
        List<Setup> initialList = repository.getSetups();

        initialList.clear();

        List<Setup> repositoryList = repository.getSetups();
        assertEquals(1, repositoryList.size());
    }

    @Test
    void shouldFindSetupByIdSuccessfully() {

        repository.addSetup(testSubject);
        repository.addSetup(secondarySetup);

        Setup foundSetup = repository.getSetupById(1L);

        assertNotNull(foundSetup);
        assertEquals("Streamer Build", foundSetup.getName());
    }

    @Test
    void shouldReturnNullWhenSetupByIdDoesNotExist() {

        repository.addSetup(testSubject);

        Setup foundSetup = repository.getSetupById(99L);

        assertNull(foundSetup);
    }

    @Test
    void shouldUpdateSetupSuccessfully() {

        repository.addSetup(testSubject);
        Setup updatedData = new Setup(1L, "Streamer Build V2", "Updated GPU", mockUser);

        Setup result = repository.updateSetup(1L, updatedData);

        assertNotNull(result);
        assertEquals("Streamer Build V2", result.getName());
        assertEquals("Updated GPU", result.getDescription());

        Setup persistenceCheck = repository.getSetupById(1L);
        assertEquals("Streamer Build V2", persistenceCheck.getName());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingSetup() {

        repository.addSetup(testSubject);
        Setup updatedData = new Setup(99L, "Ghost Setup", "Does not exist", mockUser);

        Setup result = repository.updateSetup(99L, updatedData);

        assertNull(result);
    }

    @Test
    void shouldDeleteSetupSuccessfully() {

        repository.addSetup(testSubject);
        repository.addSetup(secondarySetup);

        repository.deleteSetup(1L);

        List<Setup> allSetups = repository.getSetups();
        assertEquals(1, allSetups.size());
        assertNull(repository.getSetupById(1L));
        assertNotNull(repository.getSetupById(2L));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistingSetup() {

        repository.addSetup(testSubject);

        assertDoesNotThrow(() -> repository.deleteSetup(99L));
        assertEquals(1, repository.getSetups().size());
    }
}
