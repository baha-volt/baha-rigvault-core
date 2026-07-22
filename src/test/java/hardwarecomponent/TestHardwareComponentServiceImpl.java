package hardwarecomponent;

import domain.HardwareComponent;
import domain.RigUser;
import domain.Setup;
import exception.hardwarecomponent.DuplicateHardwareComponentException;
import exception.hardwarecomponent.HardwareComponentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.HardwareComponentRepository;
import service.hardwarecomponent.HardwareComponentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestHardwareComponentServiceImpl {

    @Mock
    private HardwareComponentRepository repository;
    private List<HardwareComponent> defaultComponents;
    private HardwareComponent testSubject;
    private Setup mockSetup;

    @InjectMocks
    private HardwareComponentServiceImpl service;

    @BeforeEach
    void setUp() {
        RigUser user = new RigUser(1L, "Alejandro", "ale@test.com", "pass123456");
        mockSetup = new Setup(1L, "Test Setup", "Specs", user);

        service = new HardwareComponentServiceImpl(repository);

        defaultComponents = new ArrayList<>();
        defaultComponents.add(new HardwareComponent(1L, "GPU", "NVIDIA", "RTX 3080", 1, mockSetup));
        defaultComponents.add(new HardwareComponent(2L, "RAM", "Corsair", "32GB", 2, mockSetup));

        testSubject = defaultComponents.get(1);
    }

    @Test
    void shouldCreateServiceSuccessfully() {
        // Act and Assert
        assertNotNull(service);
    }

    @Test
    void shouldFindAllComponents() {
        // Arrange
        when(repository.getComponents()).thenReturn(defaultComponents);

        // Act
        List<HardwareComponent> result = service.findAll();

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(testSubject.getModel(), result.get(1).getModel())
        );
    }

    @Test
    void shouldFindComponentById() {
        // Arrange
        Long id = 2L;
        when(repository.getComponentById(id)).thenReturn(testSubject);

        // Act
        HardwareComponent component = service.findById(id);

        // Assert
        assertAll(
                () -> assertNotNull(component),
                () -> assertSame(testSubject, component)
        );

        verify(repository).getComponentById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowHardwareComponentNotFoundExceptionWhenFindingNonExistingComponent() {
        // Arrange
        Long id = 5L;
        when(repository.getComponentById(id)).thenReturn(null);

        // Act and Assert
        HardwareComponentNotFoundException ex = assertThrows(
                HardwareComponentNotFoundException.class,
                () -> service.findById(id));
        assertEquals("Hardware component not found", ex.getMessage());

        verify(repository).getComponentById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldExecuteValidationMethodsDuringSaveOperation() {
        // Arrange
        Long id = 3L;
        HardwareComponent spyComponent = spy(new HardwareComponent(id, "CPU", "AMD", "Ryzen 9", 1, mockSetup));

        when(repository.getComponentById(id)).thenReturn(null);
        when(repository.addComponent(any(HardwareComponent.class))).thenReturn(spyComponent);

        // Act
        service.save(spyComponent);

        // Assert
        verify(spyComponent).validateType("CPU");
        verify(spyComponent).validateBrand("AMD");
        verify(spyComponent).validateModel("Ryzen 9");
        verify(spyComponent).validateQuantity(1);
        verify(spyComponent).validateSetup(mockSetup);
        verify(repository).addComponent(spyComponent);
    }

    @Test
    void shouldThrowDuplicateHardwareComponentExceptionWhenSavingExistingId() {
        // Arrange
        Long id = 2L;
        when(repository.getComponentById(id)).thenReturn(testSubject);

        // Act and Assert
        DuplicateHardwareComponentException ex = assertThrows(
                DuplicateHardwareComponentException.class,
                () -> service.save(testSubject));
        assertEquals("Hardware component already exists", ex.getMessage());
    }

    @Test
    void shouldUpdateComponentSuccessfully() {
        // Arrange
        Long id = 2L;
        HardwareComponent spyComponent = spy(new HardwareComponent(id, "RAM", "Corsair", "64GB", 4, mockSetup));

        when(repository.getComponentById(id)).thenReturn(testSubject);
        when(repository.updateComponent(id, spyComponent)).thenReturn(spyComponent);

        // Act
        HardwareComponent updatedComponent = service.update(id, spyComponent);

        // Assert
        assertAll(
                () -> assertNotNull(updatedComponent),
                () -> assertSame(spyComponent, updatedComponent)
        );

        verify(spyComponent).validateType("RAM");
        verify(spyComponent).validateBrand("Corsair");
        verify(spyComponent).validateModel("64GB");
        verify(spyComponent).validateQuantity(4);
        verify(spyComponent).validateSetup(mockSetup);
        verify(repository).getComponentById(id);
        verify(repository).updateComponent(id, spyComponent);
    }

    @Test
    void shouldThrowHardwareComponentNotFoundExceptionWhenUpdatingNonExistingComponent() {
        // Arrange
        Long id = 5L;
        HardwareComponent anyComponent = new HardwareComponent(id, "SSD", "Samsung", "1TB", 1, mockSetup);
        when(repository.getComponentById(id)).thenReturn(null);

        // Act and Assert
        HardwareComponentNotFoundException ex = assertThrows(
                HardwareComponentNotFoundException.class,
                () -> service.update(id, anyComponent)
        );

        assertEquals("Hardware component not found", ex.getMessage());
        verify(repository).getComponentById(id);
        verify(repository, never()).updateComponent(anyLong(), any(HardwareComponent.class));
    }

    @Test
    void shouldDeleteComponentSuccessfully() {
        // Arrange
        Long id = 2L;
        when(repository.getComponentById(id)).thenReturn(testSubject);

        // Act and Assert
        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository).getComponentById(id);
        verify(repository).deleteComponent(id);
    }

    @Test
    void shouldThrowHardwareComponentNotFoundExceptionWhenDeletingNonExistingComponent() {
        // Arrange
        Long id = 5L;
        when(repository.getComponentById(id)).thenReturn(null);

        // Act and Assert
        HardwareComponentNotFoundException ex = assertThrows(
                HardwareComponentNotFoundException.class,
                () -> service.deleteById(id)
        );

        assertEquals("Hardware component not found", ex.getMessage());
        verify(repository).getComponentById(id);
        verify(repository, never()).deleteComponent(anyLong());
    }
}