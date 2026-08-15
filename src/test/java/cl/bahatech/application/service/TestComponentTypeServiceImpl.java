package cl.bahatech.application.service;

import cl.bahatech.domain.entity.ComponentType;
import cl.bahatech.domain.exception.ComponentTypeNotFoundException;
import cl.bahatech.domain.exception.DuplicateComponentTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.bahatech.domain.repository.ComponentTypeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestComponentTypeServiceImpl {

    @Mock
    private ComponentTypeRepository repository;
    private List<ComponentType> defaultTypes;
    private ComponentType testSubject;

    @InjectMocks
    private ComponentTypeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ComponentTypeServiceImpl(repository);

        defaultTypes = new ArrayList<>();
        defaultTypes.add(new ComponentType(1L, "GPU", "Graphics processing unit"));
        defaultTypes.add(new ComponentType(2L, "CPU", "Central processing unit"));

        testSubject = defaultTypes.get(1);
    }

    @Test
    void shouldCreateServiceSuccessfully() {

        assertNotNull(service);
    }

    @Test
    void shouldFindAllComponentTypes() {

        when(repository.getComponentTypes()).thenReturn(defaultTypes);

        List<ComponentType> result = service.findAll();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(testSubject.getName(), result.get(1).getName())
        );
    }

    @Test
    void shouldFindComponentTypeById() {

        Long id = 2L;
        when(repository.getComponentTypeById(id)).thenReturn(testSubject);

        ComponentType componentType = service.findById(id);

        assertAll(
                () -> assertNotNull(componentType),
                () -> assertSame(testSubject, componentType)
        );

        verify(repository).getComponentTypeById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowComponentTypeNotFoundExceptionWhenFindingNonExistingType() {

        Long id = 5L;
        when(repository.getComponentTypeById(id)).thenReturn(null);

        ComponentTypeNotFoundException ex = assertThrows(
                ComponentTypeNotFoundException.class,
                () -> service.findById(id));
        assertEquals("Component type not found", ex.getMessage());

        verify(repository).getComponentTypeById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldSaveComponentTypeSuccessfullyWhenNameDoesNotExist() {

        ComponentType newType = new ComponentType(3L, "RAM", "Random access memory");

        when(repository.getComponentTypeByName("RAM")).thenReturn(null);
        when(repository.addComponentType(newType)).thenReturn(newType);

        ComponentType result = service.save(newType);

        assertSame(newType, result);
        verify(repository).getComponentTypeByName("RAM");
        verify(repository).addComponentType(newType);
    }

    @Test
    void shouldThrowDuplicateComponentTypeExceptionWhenNameAlreadyExists() {

        ComponentType duplicateType = new ComponentType(3L, "CPU", "Another description");
        when(repository.getComponentTypeByName("CPU")).thenReturn(testSubject);

        DuplicateComponentTypeException ex = assertThrows(
                DuplicateComponentTypeException.class,
                () -> service.save(duplicateType));
        assertEquals("Component type already exists", ex.getMessage());
    }

    @Test
    void shouldUpdateComponentTypeSuccessfully() {

        Long id = 2L;
        ComponentType updatedTypeData = new ComponentType(id, "CPU (updated)", "Updated description");

        when(repository.getComponentTypeById(id)).thenReturn(testSubject);
        when(repository.updateComponentType(id, updatedTypeData)).thenReturn(updatedTypeData);

        ComponentType updatedType = service.update(id, updatedTypeData);

        assertAll(
                () -> assertNotNull(updatedType),
                () -> assertSame(updatedTypeData, updatedType)
        );

        verify(repository).getComponentTypeById(id);
        verify(repository).updateComponentType(id, updatedTypeData);
    }

    @Test
    void shouldThrowComponentTypeNotFoundExceptionWhenUpdatingNonExistingType() {

        Long id = 5L;
        ComponentType anyType = new ComponentType(id, "SSD", "Solid state drive");
        when(repository.getComponentTypeById(id)).thenReturn(null);

        ComponentTypeNotFoundException ex = assertThrows(
                ComponentTypeNotFoundException.class,
                () -> service.update(id, anyType)
        );

        assertEquals("Component type not found", ex.getMessage());
        verify(repository).getComponentTypeById(id);
        verify(repository, never()).updateComponentType(anyLong(), any(ComponentType.class));
    }

    @Test
    void shouldDeleteComponentTypeSuccessfully() {

        Long id = 2L;
        when(repository.getComponentTypeById(id)).thenReturn(testSubject);

        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository).getComponentTypeById(id);
        verify(repository).deleteComponentType(id);
    }

    @Test
    void shouldThrowComponentTypeNotFoundExceptionWhenDeletingNonExistingType() {

        Long id = 5L;
        when(repository.getComponentTypeById(id)).thenReturn(null);

        ComponentTypeNotFoundException ex = assertThrows(
                ComponentTypeNotFoundException.class,
                () -> service.deleteById(id)
        );

        assertEquals("Component type not found", ex.getMessage());
        verify(repository).getComponentTypeById(id);
        verify(repository, never()).deleteComponentType(anyLong());
    }
}
