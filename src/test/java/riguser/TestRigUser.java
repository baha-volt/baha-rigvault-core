package riguser;

import domain.RigUser;
import exception.riguser.InvalidRigUserEmailException;
import exception.riguser.InvalidRigUserNameException;
import exception.riguser.InvalidRigUserPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class TestRigUser {

    private RigUser rigUser;

    @BeforeEach
    void setUp() {
        rigUser = new RigUser();
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenNameIsNull() {
        //Arrange
        String nullName = null;

        //Act and assert
        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(nullName));

        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenNameIsBlank() {

        //Arrange
        String blank = "    ";

        //Act and Assert
        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(blank));

        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenNameIsTooShort() {
        //Arrange
        String shortName = "Ab";

        //Act and assert
        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(shortName));

        assertEquals("Name must contain between 3 and 100 characters", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenNameIsTooLong() {
        //Arrange
        String longName = "a".repeat(101);

        //Act and assert
        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(longName));

        assertEquals("Name must contain between 3 and 100 characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ana", "Alejandro",
    "MaximilianicodemoDeLaSantisimaTrinidadJuanCarlosEustaquioGeronimoWenceslaoDeLosRiosYValenzuelaSanMar"})
    void shouldBeValidName(String name) {
        //Act and Assert
        assertDoesNotThrow(() -> rigUser.validateName(name));
    }


    @Test
    void shouldThrowInvalidRigUserEmailExceptionWhenEmailIsNull() {
        //Arrange
        String nullEmail = null;

        //Act and assert
        InvalidRigUserEmailException ex = assertThrows(
                InvalidRigUserEmailException.class,
                () -> rigUser.validateEmail(nullEmail));
        assertEquals("Invalid email", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserEmailExceptionWhenEmailIsBlank() {
        //Arrange
        String blankEmail = "      ";

        //Act and assert
        InvalidRigUserEmailException ex = assertThrows(
                InvalidRigUserEmailException.class,
                () -> rigUser.validateEmail(blankEmail));
        assertEquals("Invalid email", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan", "juan@", "@gmail.com", "juan@gmail", "juan@gmail.", "juan@.com", "juan@@gmail.com", "juan gmail@test.com", "juan#gmail.com", "juan@test.c", "juan@test,com"})
    void shouldThrowInvalidRigUserEmailExceptionWhenEmailDoesNotMatchThePattern(String email) {
        //Act and assert
        InvalidRigUserEmailException ex = assertThrows(
                InvalidRigUserEmailException.class,
                () -> rigUser.validateEmail(email));
        assertEquals("Invalid e-mail format", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan@test.com", "bahamut@gmail.cl", "nombre.apellido@hotmail.com", "usuario123@empresa.org", "a+b@test.io", "user_name@test.net", "user-name@test.co.uk"})
    void shouldValidateEmailSuccessfully(String email) {
        //Act and assert
        assertDoesNotThrow(() -> rigUser.validateEmail(email));
    }


    @Test
    void shouldThrowInvalidRigUserPasswordExceptionWhenPasswordIsNull() {
        //Arrange
        String nullPassword = null;

        //Act and assert
        InvalidRigUserPasswordException ex = assertThrows(
                InvalidRigUserPasswordException.class,
                () -> rigUser.validatePassword(nullPassword));
        assertEquals("Invalid password", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserPasswordExceptionWhenPasswordIsBlank() {
        //Arrange
        String blankPassword = "         ";

        //Act and assert
        InvalidRigUserPasswordException ex = assertThrows(
                InvalidRigUserPasswordException.class,
                () -> rigUser.validatePassword(blankPassword));
        assertEquals("Invalid password", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"dsafhb/*v", "asi98"})
    void shouldThrowInvalidRigUserPasswordExceptionWhenPasswordIsTooShort(String password) {
        //Act and assert
        InvalidRigUserPasswordException ex = assertThrows(
                InvalidRigUserPasswordException.class,
                () -> rigUser.validatePassword(password));
        assertEquals("Password must contain at least 10 characters", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"dsafhb/*vx", "mrm390&%atrix697*"})
    void shouldValidatePasswordSuccessfully(String password) {
        //Act and assert
        assertDoesNotThrow(() -> rigUser.validatePassword(password));
    }

    @Test
    void shouldCreateRigUserWithConstructor() {

        // Arrange
        Long id = 1L;
        String name = "Bahamut";
        String email = "bahamut@test.com";
        String password = "Password123";

        // Act
        RigUser user = new RigUser(id, name, email, password);

        // Assert
        assertAll(
                () -> assertEquals(id, user.getId()),
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(password, user.getPassword())
        );
    }


    @Test
    void shouldCreateRigUserWithEmptyConstructor() {

        // Arrange
        Long id = 1L;
        String name = "Bahamut";
        String email = "bahamut@test.com";
        String password = "Password123";

        // Act
        rigUser.setId(id);
        rigUser.setName(name);
        rigUser.setEmail(email);
        rigUser.setPassword(password);

        // Assert
        assertAll(
                () -> assertEquals(id, rigUser.getId()),
                () -> assertEquals(name, rigUser.getName()),
                () -> assertEquals(email, rigUser.getEmail()),
                () -> assertEquals(password, rigUser.getPassword())
        );
    }
}