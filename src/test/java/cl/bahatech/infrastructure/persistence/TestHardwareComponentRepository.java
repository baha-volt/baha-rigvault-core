package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.ComponentType;
import cl.bahatech.domain.entity.HardwareComponent;
import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.entity.Setup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cl.bahatech.domain.repository.HardwareComponentRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestHardwareComponentRepository {

    private HardwareComponentRepository repository;
    private HardwareComponent testSubject;
    private HardwareComponent secondaryComponent;
    private Setup mockSetup;
    private ComponentType gpuType;
    private ComponentType cpuType;
    private ComponentType ramType;

    @BeforeEach
    void setUp() {
        repository = new InMemoryHardwareComponentRepository();
        RigUser user = new RigUser(1L, "Alejandro", "ale@test.com", "pass123456");
        mockSetup = new Setup(1L, "Test Setup", "Specs", user);

        gpuType = new ComponentType(1L, "GPU", "Graphics processing unit");
        cpuType = new ComponentType(2L, "CPU", "Central processing unit");
        ramType = new ComponentType(3L, "RAM", "Random access memory");

        testSubject = new HardwareComponent(1L, gpuType, "NVIDIA", "RTX 3080", 1, mockSetup);
        secondaryComponent = new HardwareComponent(2L, cpuType, "AMD", "Ryzen 7", 1, mockSetup);
    }

    @Test
    void shouldAddComponentSuccessfully() {

        HardwareComponent savedComponent = repository.addComponent(testSubject);

        assertNotNull(savedComponent);
        assertEquals(testSubject.getId(), savedComponent.getId());

        List<HardwareComponent> allComponents = repository.getComponents();
        assertEquals(1, allComponents.size());
        assertTrue(allComponents.contains(testSubject));
    }

    @Test
    void shouldReturnAllComponents() {

        repository.addComponent(testSubject);
        repository.addComponent(secondaryComponent);

        List<HardwareComponent> result = repository.getComponents();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(testSubject)),
                () -> assertTrue(result.contains(secondaryComponent))
        );
    }

    @Test
    void shouldReturnEncapsulatedListToPreventExternalModification() {

        repository.addComponent(testSubject);
        List<HardwareComponent> initialList = repository.getComponents();

        initialList.clear();

        List<HardwareComponent> repositoryList = repository.getComponents();
        assertEquals(1, repositoryList.size());
    }

    @Test
    void shouldFindComponentByIdSuccessfully() {

        repository.addComponent(testSubject);
        repository.addComponent(secondaryComponent);

        HardwareComponent foundComponent = repository.getComponentById(1L);

        assertNotNull(foundComponent);
        assertEquals("RTX 3080", foundComponent.getModel());
    }

    @Test
    void shouldReturnNullWhenComponentByIdDoesNotExist() {

        repository.addComponent(testSubject);

        HardwareComponent foundComponent = repository.getComponentById(99L);

        assertNull(foundComponent);
    }

    @Test
    void shouldUpdateComponentSuccessfully() {

        repository.addComponent(testSubject);
        HardwareComponent updatedData = new HardwareComponent(1L, gpuType, "NVIDIA", "RTX 4090", 2, mockSetup);

        HardwareComponent result = repository.updateComponent(1L, updatedData);

        assertNotNull(result);
        assertEquals("RTX 4090", result.getModel());
        assertEquals(2, result.getQuantity());

        HardwareComponent persistenceCheck = repository.getComponentById(1L);
        assertEquals("RTX 4090", persistenceCheck.getModel());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingComponent() {

        repository.addComponent(testSubject);
        HardwareComponent updatedData = new HardwareComponent(99L, ramType, "Corsair", "16GB", 2, mockSetup);

        HardwareComponent result = repository.updateComponent(99L, updatedData);

        assertNull(result);
    }

    @Test
    void shouldDeleteComponentSuccessfully() {

        repository.addComponent(testSubject);
        repository.addComponent(secondaryComponent);

        repository.deleteComponent(1L);

        List<HardwareComponent> allComponents = repository.getComponents();
        assertEquals(1, allComponents.size());
        assertNull(repository.getComponentById(1L));
        assertNotNull(repository.getComponentById(2L));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistingComponent() {

        repository.addComponent(testSubject);

        assertDoesNotThrow(() -> repository.deleteComponent(99L));
        assertEquals(1, repository.getComponents().size());
    }
}
