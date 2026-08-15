package cl.bahatech.application.service;

import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.entity.Setup;
import cl.bahatech.domain.exception.DuplicateSetupException;
import cl.bahatech.domain.exception.SetupNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.bahatech.domain.repository.SetupRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSetupServiceImpl {

    @Mock
    private SetupRepository repository;
    private List<Setup> defaultSetups;
    private Setup testSubject;
    private RigUser mockUser;

    @InjectMocks
    private SetupServiceImpl service;

    @BeforeEach
    void setUp() {
        mockUser = new RigUser(1L, "Alejandro", "ale@test.com", "pass123456");
        service = new SetupServiceImpl(repository);

        defaultSetups = new ArrayList<>();
        defaultSetups.add(new Setup(1L, "Rig 1", "Home setup", mockUser));
        defaultSetups.add(new Setup(2L, "Rig 2", "Work setup", mockUser));

        testSubject = defaultSetups.get(1);
    }

    @Test
    void shouldCreateServiceSuccessfully() {

        assertNotNull(service);
    }

    @Test
    void shouldFindAllSetups() {

        when(repository.getSetups()).thenReturn(defaultSetups);

        List<Setup> result = service.findAll();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(testSubject.getName(), result.get(1).getName())
        );
    }

    @Test
    void shouldFindSetupById() {

        Long id = 2L;
        when(repository.getSetupById(id)).thenReturn(testSubject);

        Setup setup = service.findById(id);

        assertAll(
                () -> assertNotNull(setup),
                () -> assertSame(testSubject, setup)
        );

        verify(repository).getSetupById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowSetupNotFoundExceptionWhenFindingNonExistingSetup() {

        Long id = 5L;
        when(repository.getSetupById(id)).thenReturn(null);

        SetupNotFoundException ex = assertThrows(
                SetupNotFoundException.class,
                () -> service.findById(id));
        assertEquals("Setup not found", ex.getMessage());

        verify(repository).getSetupById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldSaveSetupSuccessfullyWhenIdDoesNotExist() {

        Long id = 3L;
        Setup newSetup = new Setup(id, "Editing Rig", "Video editing machine", mockUser);

        when(repository.getSetupById(id)).thenReturn(null);
        when(repository.addSetup(newSetup)).thenReturn(newSetup);

        Setup result = service.save(newSetup);

        assertSame(newSetup, result);
        verify(repository).getSetupById(id);
        verify(repository).addSetup(newSetup);
    }

    @Test
    void shouldThrowDuplicateSetupExceptionWhenSavingExistingId() {

        Long id = 2L;
        when(repository.getSetupById(id)).thenReturn(testSubject);

        DuplicateSetupException ex = assertThrows(
                DuplicateSetupException.class,
                () -> service.save(testSubject));
        assertEquals("Setup already exists", ex.getMessage());
    }

    @Test
    void shouldUpdateSetupSuccessfully() {

        Long id = 2L;
        Setup updatedSetupData = new Setup(id, "Rig 2 Modded", "Updated work setup", mockUser);

        when(repository.getSetupById(id)).thenReturn(testSubject);
        when(repository.updateSetup(id, updatedSetupData)).thenReturn(updatedSetupData);

        Setup updatedSetup = service.update(id, updatedSetupData);

        assertAll(
                () -> assertNotNull(updatedSetup),
                () -> assertSame(updatedSetupData, updatedSetup)
        );

        verify(repository).getSetupById(id);
        verify(repository).updateSetup(id, updatedSetupData);
    }

    @Test
    void shouldThrowSetupNotFoundExceptionWhenUpdatingNonExistingSetup() {

        Long id = 5L;
        Setup anySetup = new Setup(id, "Phantom Rig", "Unknown", mockUser);
        when(repository.getSetupById(id)).thenReturn(null);

        SetupNotFoundException ex = assertThrows(
                SetupNotFoundException.class,
                () -> service.update(id, anySetup)
        );

        assertEquals("Setup not found", ex.getMessage());
        verify(repository).getSetupById(id);
        verify(repository, never()).updateSetup(anyLong(), any(Setup.class));
    }

    @Test
    void shouldDeleteSetupSuccessfully() {

        Long id = 2L;
        when(repository.getSetupById(id)).thenReturn(testSubject);

        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository).getSetupById(id);
        verify(repository).deleteSetup(id);
    }

    @Test
    void shouldThrowSetupNotFoundExceptionWhenDeletingNonExistingSetup() {

        Long id = 5L;
        when(repository.getSetupById(id)).thenReturn(null);

        SetupNotFoundException ex = assertThrows(
                SetupNotFoundException.class,
                () -> service.deleteById(id)
        );

        assertEquals("Setup not found", ex.getMessage());
        verify(repository).getSetupById(id);
        verify(repository, never()).deleteSetup(anyLong());
    }
}
