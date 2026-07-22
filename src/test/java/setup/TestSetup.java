package setup;

import domain.RigUser;
import domain.Setup;
import exception.setup.InvalidSetupDescriptionException;
import exception.setup.InvalidSetupNameException;
import exception.setup.InvalidSetupUserException;
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
        // Arrange
        String nullName = null;

        // Act and Assert
        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(nullName));

        assertEquals("Invalid setup name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupNameExceptionWhenNameIsBlank() {
        // Arrange
        String blankName = "   ";

        // Act and Assert
        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(blankName));

        assertEquals("Invalid setup name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupNameExceptionWhenNameIsTooShort() {
        // Arrange
        String shortName = "Ab";

        // Act and Assert
        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(shortName));

        assertEquals("Setup name must contain between 3 and 50 characters", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupNameExceptionWhenNameIsTooLong() {
        // Arrange
        String longName = "a".repeat(51);

        // Act and Assert
        InvalidSetupNameException exception = assertThrows(
                InvalidSetupNameException.class,
                () -> setup.validateName(longName));

        assertEquals("Setup name must contain between 3 and 50 characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pro Gaming Rig", "Home Server", "Workstation Alpha"})
    void shouldValidateNameSuccessfully(String name) {
        // Act and Assert
        assertDoesNotThrow(() -> setup.validateName(name));
    }

    @Test
    void shouldThrowInvalidSetupDescriptionExceptionWhenDescriptionIsNull() {
        // Arrange
        String nullDescription = null;

        // Act and Assert
        InvalidSetupDescriptionException exception = assertThrows(
                InvalidSetupDescriptionException.class,
                () -> setup.validateDescription(nullDescription));

        assertEquals("Invalid setup description", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupDescriptionExceptionWhenDescriptionIsBlank() {
        // Arrange
        String blankDescription = "    ";

        // Act and Assert
        InvalidSetupDescriptionException exception = assertThrows(
                InvalidSetupDescriptionException.class,
                () -> setup.validateDescription(blankDescription));

        assertEquals("Invalid setup description", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidSetupDescriptionExceptionWhenDescriptionIsTooLong() {
        // Arrange
        String longDescription = "a".repeat(256);

        // Act and Assert
        InvalidSetupDescriptionException exception = assertThrows(
                InvalidSetupDescriptionException.class,
                () -> setup.validateDescription(longDescription));

        assertEquals("Setup description cannot exceed 255 characters", exception.getMessage());
    }

    @Test
    void shouldValidateDescriptionSuccessfully() {
        // Arrange
        String validDescription = "This is a high-end gaming setup.";

        // Act and Assert
        assertDoesNotThrow(() -> setup.validateDescription(validDescription));
    }

    @Test
    void shouldThrowInvalidSetupUserExceptionWhenUserIsNull() {
        // Arrange
        RigUser nullUser = null;

        // Act and Assert
        InvalidSetupUserException exception = assertThrows(
                InvalidSetupUserException.class,
                () -> setup.validateUser(nullUser));

        assertEquals("Setup must have a valid user assigned", exception.getMessage());
    }

    @Test
    void shouldValidateUserSuccessfully() {
        // Act and Assert
        assertDoesNotThrow(() -> setup.validateUser(validUser));
    }

    @Test
    void shouldCreateSetupWithConstructor() {
        // Arrange
        Long id = 1L;
        String name = "Gaming Rig";
        String description = "RGB everywhere";

        // Act
        Setup newSetup = new Setup(id, name, description, validUser);

        // Assert
        assertAll(
                () -> assertEquals(id, newSetup.getId()),
                () -> assertEquals(name, newSetup.getName()),
                () -> assertEquals(description, newSetup.getDescription()),
                () -> assertEquals(validUser, newSetup.getUser())
        );
    }

    @Test
    void shouldCreateSetupWithEmptyConstructor() {
        // Arrange
        Long id = 2L;
        String name = "Office PC";
        String description = "Quiet and efficient";

        // Act
        setup.setId(id);
        setup.setName(name);
        setup.setDescription(description);
        setup.setUser(validUser);

        // Assert
        assertAll(
                () -> assertEquals(id, setup.getId()),
                () -> assertEquals(name, setup.getName()),
                () -> assertEquals(description, setup.getDescription()),
                () -> assertEquals(validUser, setup.getUser())
        );
    }
}