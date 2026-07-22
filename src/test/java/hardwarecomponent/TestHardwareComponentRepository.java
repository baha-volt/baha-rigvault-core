package hardwarecomponent;

import domain.HardwareComponent;
import domain.RigUser;
import domain.Setup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.HardwareComponentRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestHardwareComponentRepository {

    private HardwareComponentRepository repository;
    private HardwareComponent testSubject;
    private HardwareComponent secondaryComponent;
    private Setup mockSetup;

    @BeforeEach
    void setUp() {
        repository = new HardwareComponentRepository();
        RigUser user = new RigUser(1L, "Alejandro", "ale@test.com", "pass123456");
        mockSetup = new Setup(1L, "Test Setup", "Specs", user);

        testSubject = new HardwareComponent(1L, "GPU", "NVIDIA", "RTX 3080", 1, mockSetup);
        secondaryComponent = new HardwareComponent(2L, "CPU", "AMD", "Ryzen 7", 1, mockSetup);
    }

    @Test
    void shouldAddComponentSuccessfully() {
        // Act
        HardwareComponent savedComponent = repository.addComponent(testSubject);

        // Assert
        assertNotNull(savedComponent);
        assertEquals(testSubject.getId(), savedComponent.getId());

        List<HardwareComponent> allComponents = repository.getComponents();
        assertEquals(1, allComponents.size());
        assertTrue(allComponents.contains(testSubject));
    }

    @Test
    void shouldReturnAllComponents() {
        // Arrange
        repository.addComponent(testSubject);
        repository.addComponent(secondaryComponent);

        // Act
        List<HardwareComponent> result = repository.getComponents();

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(testSubject)),
                () -> assertTrue(result.contains(secondaryComponent))
        );
    }

    @Test
    void shouldReturnEncapsulatedListToPreventExternalModification() {
        // Arrange
        repository.addComponent(testSubject);
        List<HardwareComponent> initialList = repository.getComponents();

        // Act
        initialList.clear();

        // Assert
        List<HardwareComponent> repositoryList = repository.getComponents();
        assertEquals(1, repositoryList.size());
    }

    @Test
    void shouldFindComponentByIdSuccessfully() {
        // Arrange
        repository.addComponent(testSubject);
        repository.addComponent(secondaryComponent);

        // Act
        HardwareComponent foundComponent = repository.getComponentById(1L);

        // Assert
        assertNotNull(foundComponent);
        assertEquals("RTX 3080", foundComponent.getModel());
    }

    @Test
    void shouldReturnNullWhenComponentByIdDoesNotExist() {
        // Arrange
        repository.addComponent(testSubject);

        // Act
        HardwareComponent foundComponent = repository.getComponentById(99L);

        // Assert
        assertNull(foundComponent);
    }

    @Test
    void shouldUpdateComponentSuccessfully() {
        // Arrange
        repository.addComponent(testSubject);
        HardwareComponent updatedData = new HardwareComponent(1L, "GPU", "NVIDIA", "RTX 4090", 2, mockSetup);

        // Act
        HardwareComponent result = repository.updateComponent(1L, updatedData);

        // Assert
        assertNotNull(result);
        assertEquals("RTX 4090", result.getModel());
        assertEquals(2, result.getQuantity());

        HardwareComponent persistenceCheck = repository.getComponentById(1L);
        assertEquals("RTX 4090", persistenceCheck.getModel());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingComponent() {
        // Arrange
        repository.addComponent(testSubject);
        HardwareComponent updatedData = new HardwareComponent(99L, "RAM", "Corsair", "16GB", 2, mockSetup);

        // Act
        HardwareComponent result = repository.updateComponent(99L, updatedData);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldDeleteComponentSuccessfully() {
        // Arrange
        repository.addComponent(testSubject);
        repository.addComponent(secondaryComponent);

        // Act
        repository.deleteComponent(1L);

        // Assert
        List<HardwareComponent> allComponents = repository.getComponents();
        assertEquals(1, allComponents.size());
        assertNull(repository.getComponentById(1L));
        assertNotNull(repository.getComponentById(2L));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistingComponent() {
        // Arrange
        repository.addComponent(testSubject);

        // Act and Assert
        assertDoesNotThrow(() -> repository.deleteComponent(99L));
        assertEquals(1, repository.getComponents().size());
    }
}