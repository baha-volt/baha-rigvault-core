package setup;

import domain.RigUser;
import domain.Setup;
import exception.setup.DuplicateSetupException;
import exception.setup.SetupNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.SetupRepository;
import service.setup.SetupServiceImpl;
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
        // Act and Assert
        assertNotNull(service);
    }

    @Test
    void shouldFindAllSetups() {
        // Arrange
        when(repository.getSetups()).thenReturn(defaultSetups);

        // Act
        List<Setup> result = service.findAll();

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(testSubject.getName(), result.get(1).getName())
        );
    }

    @Test
    void shouldFindSetupById() {
        // Arrange
        Long id = 2L;
        when(repository.getSetupById(id)).thenReturn(testSubject);

        // Act
        Setup setup = service.findById(id);

        // Assert
        assertAll(
                () -> assertNotNull(setup),
                () -> assertSame(testSubject, setup)
        );

        verify(repository).getSetupById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowSetupNotFoundExceptionWhenFindingNonExistingSetup() {
        // Arrange
        Long id = 5L;
        when(repository.getSetupById(id)).thenReturn(null);

        // Act and Assert
        SetupNotFoundException ex = assertThrows(
                SetupNotFoundException.class,
                () -> service.findById(id));
        assertEquals("Setup not found", ex.getMessage());

        verify(repository).getSetupById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldExecuteValidationMethodsDuringSaveOperation() {
        // Arrange
        Long id = 3L;
        Setup spySetup = spy(new Setup(id, "Editing Rig", "Video editing machine", mockUser));

        when(repository.getSetupById(id)).thenReturn(null);
        when(repository.addSetup(any(Setup.class))).thenReturn(spySetup);

        // Act
        service.save(spySetup);

        // Assert
        verify(spySetup).validateName("Editing Rig");
        verify(spySetup).validateDescription("Video editing machine");
        verify(spySetup).validateUser(mockUser);
        verify(repository).addSetup(spySetup);
    }

    @Test
    void shouldThrowDuplicateSetupExceptionWhenSavingExistingId() {
        // Arrange
        Long id = 2L;
        when(repository.getSetupById(id)).thenReturn(testSubject);

        // Act and Assert
        DuplicateSetupException ex = assertThrows(
                DuplicateSetupException.class,
                () -> service.save(testSubject));
        assertEquals("Setup already exists", ex.getMessage());
    }

    @Test
    void shouldUpdateSetupSuccessfully() {
        // Arrange
        Long id = 2L;
        Setup spySetup = spy(new Setup(id, "Rig 2 Modded", "Updated work setup", mockUser));

        when(repository.getSetupById(id)).thenReturn(testSubject);
        when(repository.updateSetup(id, spySetup)).thenReturn(spySetup);

        // Act
        Setup updatedSetup = service.update(id, spySetup);

        // Assert
        assertAll(
                () -> assertNotNull(updatedSetup),
                () -> assertSame(spySetup, updatedSetup)
        );

        verify(spySetup).validateName("Rig 2 Modded");
        verify(spySetup).validateDescription("Updated work setup");
        verify(spySetup).validateUser(mockUser);
        verify(repository).getSetupById(id);
        verify(repository).updateSetup(id, spySetup);
    }

    @Test
    void shouldThrowSetupNotFoundExceptionWhenUpdatingNonExistingSetup() {
        // Arrange
        Long id = 5L;
        Setup anySetup = new Setup(id, "Phantom Rig", "Unknown", mockUser);
        when(repository.getSetupById(id)).thenReturn(null);

        // Act and Assert
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
        // Arrange
        Long id = 2L;
        when(repository.getSetupById(id)).thenReturn(testSubject);

        // Act and Assert
        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository).getSetupById(id);
        verify(repository).deleteSetup(id);
    }

    @Test
    void shouldThrowSetupNotFoundExceptionWhenDeletingNonExistingSetup() {
        // Arrange
        Long id = 5L;
        when(repository.getSetupById(id)).thenReturn(null);

        // Act and Assert
        SetupNotFoundException ex = assertThrows(
                SetupNotFoundException.class,
                () -> service.deleteById(id)
        );

        assertEquals("Setup not found", ex.getMessage());
        verify(repository).getSetupById(id);
        verify(repository, never()).deleteSetup(anyLong());
    }
}