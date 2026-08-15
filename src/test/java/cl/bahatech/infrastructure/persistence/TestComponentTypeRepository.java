package cl.bahatech.infrastructure.persistence;

import cl.bahatech.domain.entity.ComponentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cl.bahatech.domain.repository.ComponentTypeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestComponentTypeRepository {

    private ComponentTypeRepository repository;
    private ComponentType testSubject;
    private ComponentType secondaryType;

    @BeforeEach
    void setUp() {
        repository = new InMemoryComponentTypeRepository();

        testSubject = new ComponentType(1L, "GPU", "Graphics processing unit");
        secondaryType = new ComponentType(2L, "CPU", "Central processing unit");
    }

    @Test
    void shouldAddComponentTypeSuccessfully() {

        ComponentType savedType = repository.addComponentType(testSubject);

        assertNotNull(savedType);
        assertEquals(testSubject.getId(), savedType.getId());

        List<ComponentType> allTypes = repository.getComponentTypes();
        assertEquals(1, allTypes.size());
        assertTrue(allTypes.contains(testSubject));
    }

    @Test
    void shouldReturnAllComponentTypes() {

        repository.addComponentType(testSubject);
        repository.addComponentType(secondaryType);

        List<ComponentType> result = repository.getComponentTypes();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertTrue(result.contains(testSubject)),
                () -> assertTrue(result.contains(secondaryType))
        );
    }

    @Test
    void shouldReturnEncapsulatedListToPreventExternalModification() {

        repository.addComponentType(testSubject);
        List<ComponentType> initialList = repository.getComponentTypes();

        initialList.clear();

        List<ComponentType> repositoryList = repository.getComponentTypes();
        assertEquals(1, repositoryList.size());
    }

    @Test
    void shouldFindComponentTypeByIdSuccessfully() {

        repository.addComponentType(testSubject);
        repository.addComponentType(secondaryType);

        ComponentType foundType = repository.getComponentTypeById(1L);

        assertNotNull(foundType);
        assertEquals("GPU", foundType.getName());
    }

    @Test
    void shouldReturnNullWhenComponentTypeByIdDoesNotExist() {

        repository.addComponentType(testSubject);

        ComponentType foundType = repository.getComponentTypeById(99L);

        assertNull(foundType);
    }

    @Test
    void shouldFindComponentTypeByNameSuccessfully() {

        repository.addComponentType(testSubject);
        repository.addComponentType(secondaryType);

        ComponentType foundType = repository.getComponentTypeByName("gpu");

        assertNotNull(foundType);
        assertEquals(1L, foundType.getId());
    }

    @Test
    void shouldReturnNullWhenComponentTypeByNameDoesNotExist() {

        repository.addComponentType(testSubject);

        ComponentType foundType = repository.getComponentTypeByName("RAM");

        assertNull(foundType);
    }

    @Test
    void shouldUpdateComponentTypeSuccessfully() {

        repository.addComponentType(testSubject);
        ComponentType updatedData = new ComponentType(1L, "GPU (dedicated)", "Discrete graphics processing unit");

        ComponentType result = repository.updateComponentType(1L, updatedData);

        assertNotNull(result);
        assertEquals("GPU (dedicated)", result.getName());

        ComponentType persistenceCheck = repository.getComponentTypeById(1L);
        assertEquals("GPU (dedicated)", persistenceCheck.getName());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistingComponentType() {

        repository.addComponentType(testSubject);
        ComponentType updatedData = new ComponentType(99L, "Unknown", "Does not exist");

        ComponentType result = repository.updateComponentType(99L, updatedData);

        assertNull(result);
    }

    @Test
    void shouldDeleteComponentTypeSuccessfully() {

        repository.addComponentType(testSubject);
        repository.addComponentType(secondaryType);

        repository.deleteComponentType(1L);

        List<ComponentType> allTypes = repository.getComponentTypes();
        assertEquals(1, allTypes.size());
        assertNull(repository.getComponentTypeById(1L));
        assertNotNull(repository.getComponentTypeById(2L));
    }

    @Test
    void shouldDoNothingWhenDeletingNonExistingComponentType() {

        repository.addComponentType(testSubject);

        assertDoesNotThrow(() -> repository.deleteComponentType(99L));
        assertEquals(1, repository.getComponentTypes().size());
    }
}
