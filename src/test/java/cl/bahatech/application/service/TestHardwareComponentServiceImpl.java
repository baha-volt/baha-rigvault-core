package cl.bahatech.application.service;

import cl.bahatech.domain.entity.ComponentType;
import cl.bahatech.domain.entity.HardwareComponent;
import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.entity.Setup;
import cl.bahatech.domain.exception.DuplicateHardwareComponentException;
import cl.bahatech.domain.exception.HardwareComponentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.bahatech.domain.repository.HardwareComponentRepository;

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
    private ComponentType gpuType;
    private ComponentType ramType;
    private ComponentType cpuType;
    private ComponentType ssdType;

    @InjectMocks
    private HardwareComponentServiceImpl service;

    @BeforeEach
    void setUp() {
        RigUser user = new RigUser(1L, "Alejandro", "ale@test.com", "pass123456");
        mockSetup = new Setup(1L, "Test Setup", "Specs", user);

        gpuType = new ComponentType(1L, "GPU", "Graphics processing unit");
        ramType = new ComponentType(2L, "RAM", "Random access memory");
        cpuType = new ComponentType(3L, "CPU", "Central processing unit");
        ssdType = new ComponentType(4L, "SSD", "Solid state drive");

        service = new HardwareComponentServiceImpl(repository);

        defaultComponents = new ArrayList<>();
        defaultComponents.add(new HardwareComponent(1L, gpuType, "NVIDIA", "RTX 3080", 1, mockSetup));
        defaultComponents.add(new HardwareComponent(2L, ramType, "Corsair", "32GB", 2, mockSetup));

        testSubject = defaultComponents.get(1);
    }

    @Test
    void shouldCreateServiceSuccessfully() {

        assertNotNull(service);
    }

    @Test
    void shouldFindAllComponents() {

        when(repository.getComponents()).thenReturn(defaultComponents);

        List<HardwareComponent> result = service.findAll();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(testSubject.getModel(), result.get(1).getModel())
        );
    }

    @Test
    void shouldFindComponentById() {

        Long id = 2L;
        when(repository.getComponentById(id)).thenReturn(testSubject);

        HardwareComponent component = service.findById(id);

        assertAll(
                () -> assertNotNull(component),
                () -> assertSame(testSubject, component)
        );

        verify(repository).getComponentById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowHardwareComponentNotFoundExceptionWhenFindingNonExistingComponent() {

        Long id = 5L;
        when(repository.getComponentById(id)).thenReturn(null);

        HardwareComponentNotFoundException ex = assertThrows(
                HardwareComponentNotFoundException.class,
                () -> service.findById(id));
        assertEquals("Hardware component not found", ex.getMessage());

        verify(repository).getComponentById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldSaveComponentSuccessfullyWhenIdDoesNotExist() {

        Long id = 3L;
        HardwareComponent newComponent = new HardwareComponent(id, cpuType, "AMD", "Ryzen 9", 1, mockSetup);

        when(repository.getComponentById(id)).thenReturn(null);
        when(repository.addComponent(newComponent)).thenReturn(newComponent);

        HardwareComponent result = service.save(newComponent);

        assertSame(newComponent, result);
        verify(repository).getComponentById(id);
        verify(repository).addComponent(newComponent);
    }

    @Test
    void shouldThrowDuplicateHardwareComponentExceptionWhenSavingExistingId() {

        Long id = 2L;
        when(repository.getComponentById(id)).thenReturn(testSubject);

        DuplicateHardwareComponentException ex = assertThrows(
                DuplicateHardwareComponentException.class,
                () -> service.save(testSubject));
        assertEquals("Hardware component already exists", ex.getMessage());
    }

    @Test
    void shouldUpdateComponentSuccessfully() {

        Long id = 2L;
        HardwareComponent updatedComponentData = new HardwareComponent(id, ramType, "Corsair", "64GB", 4, mockSetup);

        when(repository.getComponentById(id)).thenReturn(testSubject);
        when(repository.updateComponent(id, updatedComponentData)).thenReturn(updatedComponentData);

        HardwareComponent updatedComponent = service.update(id, updatedComponentData);

        assertAll(
                () -> assertNotNull(updatedComponent),
                () -> assertSame(updatedComponentData, updatedComponent)
        );

        verify(repository).getComponentById(id);
        verify(repository).updateComponent(id, updatedComponentData);
    }

    @Test
    void shouldThrowHardwareComponentNotFoundExceptionWhenUpdatingNonExistingComponent() {

        Long id = 5L;
        HardwareComponent anyComponent = new HardwareComponent(id, ssdType, "Samsung", "1TB", 1, mockSetup);
        when(repository.getComponentById(id)).thenReturn(null);

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

        Long id = 2L;
        when(repository.getComponentById(id)).thenReturn(testSubject);

        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository).getComponentById(id);
        verify(repository).deleteComponent(id);
    }

    @Test
    void shouldThrowHardwareComponentNotFoundExceptionWhenDeletingNonExistingComponent() {

        Long id = 5L;
        when(repository.getComponentById(id)).thenReturn(null);

        HardwareComponentNotFoundException ex = assertThrows(
                HardwareComponentNotFoundException.class,
                () -> service.deleteById(id)
        );

        assertEquals("Hardware component not found", ex.getMessage());
        verify(repository).getComponentById(id);
        verify(repository, never()).deleteComponent(anyLong());
    }
}
