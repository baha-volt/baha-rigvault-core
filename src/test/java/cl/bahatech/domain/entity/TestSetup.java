package cl.bahatech.domain.entity;

import cl.bahatech.domain.exception.InvalidSetupDescriptionException;
import cl.bahatech.domain.exception.InvalidSetupNameException;
import cl.bahatech.domain.exception.InvalidSetupUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestSetup {

    private Setup setup;
    private RigUser validUser;

    @BeforeEach
    void setUp() {
        setup = new Setup();
        validUser = new RigUser(1L, "Test User", "test@email.com", "password123");
    }

    @Test
    void shouldThrowInvalidSetupNameExceptionWhenNameIsNull() {

        String nullName = null;

        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(nullName));

        assertEquals("Invalid setup name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupNameExceptionWhenNameIsBlank() {

        String blankName = "   ";

        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(blankName));

        assertEquals("Invalid setup name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupNameExceptionWhenNameIsTooShort() {

        String shortName = "Ab";

        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(shortName));

        assertEquals("Setup name must contain between 3 and 50 characters", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupNameExceptionWhenNameIsTooLong() {

        String longName = "a".repeat(51);

        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(longName));

        assertEquals("Setup name must contain between 3 and 50 characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pro Gaming Rig", "Home Server", "Workstation Alpha"})
    void shouldValidateNameSuccessfully(String name) {

        assertDoesNotThrow(() -> setup.validateName(name));
    }

    @Test
    void shouldThrowInvalidSetupDescriptionExceptionWhenDescriptionIsNull() {

        String nullDescription = null;

        InvalidSetupDescriptionException exception = assertThrows(
                InvalidSetupDescriptionException.class,
                () -> setup.validateDescription(nullDescription));

        assertEquals("Invalid setup description", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupDescriptionExceptionWhenDescriptionIsBlank() {

        String blankDescription = "    ";

        InvalidSetupDescriptionException exception = assertThrows(
                InvalidSetupDescriptionException.class,
                () -> setup.validateDescription(blankDescription));

        assertEquals("Invalid setup description", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupDescriptionExceptionWhenDescriptionIsTooLong() {

        String longDescription = "a".repeat(256);

        InvalidSetupDescriptionException exception = assertThrows(
                InvalidSetupDescriptionException.class,
                () -> setup.validateDescription(longDescription));

        assertEquals("Setup description cannot exceed 255 characters", exception.getMessage());
    }

    @Test
    void shouldValidateDescriptionSuccessfully() {

        String validDescription = "This is a high-end gaming setup.";

        assertDoesNotThrow(() -> setup.validateDescription(validDescription));
    }

    @Test
    void shouldThrowInvalidSetupUserExceptionWhenUserIsNull() {

        RigUser nullUser = null;

        InvalidSetupUserException exception = assertThrows(
                InvalidSetupUserException.class,
                () -> setup.validateUser(nullUser));

        assertEquals("Setup must have a valid user assigned", exception.getMessage());
    }

    @Test
    void shouldValidateUserSuccessfully() {

        assertDoesNotThrow(() -> setup.validateUser(validUser));
    }

    @Test
    void shouldCreateSetupWithConstructor() {

        Long id = 1L;
        String name = "Gaming Rig";
        String description = "RGB everywhere";

        Setup newSetup = new Setup(id, name, description, validUser);

        assertAll(
                () -> assertEquals(id, newSetup.getId()),
                () -> assertEquals(name, newSetup.getName()),
                () -> assertEquals(description, newSetup.getDescription()),
                () -> assertEquals(validUser, newSetup.getUser())
        );
    }

    @Test
    void shouldCreateSetupWithEmptyConstructor() {

        Long id = 2L;
        String name = "Office PC";
        String description = "Quiet and efficient";

        setup.setId(id);
        setup.setName(name);
        setup.setDescription(description);
        setup.setUser(validUser);

        assertAll(
                () -> assertEquals(id, setup.getId()),
                () -> assertEquals(name, setup.getName()),
                () -> assertEquals(description, setup.getDescription()),
                () -> assertEquals(validUser, setup.getUser())
        );
    }
}
