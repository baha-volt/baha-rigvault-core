package cl.bahatech.domain.entity;

import cl.bahatech.domain.exception.InvalidComponentTypeDescriptionException;
import cl.bahatech.domain.exception.InvalidComponentTypeNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestComponentType {

    private ComponentType componentType;

    @BeforeEach
    void setUp() {
        componentType = new ComponentType();
    }

    @Test
    void shouldThrowInvalidComponentTypeNameExceptionWhenNameIsNull() {

        String nullName = null;

        InvalidComponentTypeNameException exception = assertThrows(
                InvalidComponentTypeNameException.class,
                () -> componentType.validateName(nullName));

        assertEquals("Invalid component type name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidComponentTypeNameExceptionWhenNameIsBlank() {

        String blankName = "   ";

        InvalidComponentTypeNameException exception = assertThrows(
                InvalidComponentTypeNameException.class,
                () -> componentType.validateName(blankName));

        assertEquals("Invalid component type name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidComponentTypeNameExceptionWhenNameIsTooShort() {

        String shortName = "G";

        InvalidComponentTypeNameException exception = assertThrows(
                InvalidComponentTypeNameException.class,
                () -> componentType.validateName(shortName));

        assertEquals("Component type name must contain between 2 and 50 characters", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidComponentTypeNameExceptionWhenNameIsTooLong() {

        String longName = "A".repeat(51);

        InvalidComponentTypeNameException exception = assertThrows(
                InvalidComponentTypeNameException.class,
                () -> componentType.validateName(longName));

        assertEquals("Component type name must contain between 2 and 50 characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"GPU", "CPU", "RAM", "Power Supply Unit"})
    void shouldValidateNameSuccessfully(String name) {

        assertDoesNotThrow(() -> componentType.validateName(name));
    }

    @Test
    void shouldThrowInvalidComponentTypeDescriptionExceptionWhenDescriptionIsNull() {

        String nullDescription = null;

        InvalidComponentTypeDescriptionException exception = assertThrows(
                InvalidComponentTypeDescriptionException.class,
                () -> componentType.validateDescription(nullDescription));

        assertEquals("Invalid component type description", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidComponentTypeDescriptionExceptionWhenDescriptionIsBlank() {

        String blankDescription = "   ";

        InvalidComponentTypeDescriptionException exception = assertThrows(
                InvalidComponentTypeDescriptionException.class,
                () -> componentType.validateDescription(blankDescription));

        assertEquals("Invalid component type description", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidComponentTypeDescriptionExceptionWhenDescriptionIsTooLong() {

        String longDescription = "A".repeat(256);

        InvalidComponentTypeDescriptionException exception = assertThrows(
                InvalidComponentTypeDescriptionException.class,
                () -> componentType.validateDescription(longDescription));

        assertEquals("Component type description cannot exceed 255 characters", exception.getMessage());
    }

    @Test
    void shouldValidateDescriptionSuccessfully() {

        assertDoesNotThrow(() -> componentType.validateDescription("Graphics processing unit"));
    }

    @Test
    void shouldCreateComponentTypeWithConstructor() {

        Long id = 1L;
        String name = "GPU";
        String description = "Graphics processing unit";

        ComponentType newComponentType = new ComponentType(id, name, description);

        assertAll(
                () -> assertEquals(id, newComponentType.getId()),
                () -> assertEquals(name, newComponentType.getName()),
                () -> assertEquals(description, newComponentType.getDescription())
        );
    }

    @Test
    void shouldThrowExceptionWhenConstructingWithInvalidName() {

        assertThrows(
                InvalidComponentTypeNameException.class,
                () -> new ComponentType(1L, "", "Graphics processing unit"));
    }

    @Test
    void shouldCreateComponentTypeWithEmptyConstructorAndSetters() {

        Long id = 2L;
        String name = "RAM";
        String description = "Random access memory";

        componentType.setId(id);
        componentType.setName(name);
        componentType.setDescription(description);

        assertAll(
                () -> assertEquals(id, componentType.getId()),
                () -> assertEquals(name, componentType.getName()),
                () -> assertEquals(description, componentType.getDescription())
        );
    }

    @Test
    void shouldThrowExceptionWhenSettingInvalidNameThroughSetter() {

        assertThrows(
                InvalidComponentTypeNameException.class,
                () -> componentType.setName(""));
    }
}
