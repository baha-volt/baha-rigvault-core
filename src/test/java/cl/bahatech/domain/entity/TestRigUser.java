package cl.bahatech.domain.entity;


import cl.bahatech.domain.exception.InvalidRigUserEmailException;
import cl.bahatech.domain.exception.InvalidRigUserNameException;
import cl.bahatech.domain.exception.InvalidRigUserPasswordException;
import cl.bahatech.domain.valueobject.Email;
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

        String nullName = null;

        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(nullName));

        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenNameIsBlank() {

        String blank = "    ";

        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(blank));

        assertEquals("Invalid name", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenNameIsTooShort() {

        String shortName = "Ab";

        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(shortName));

        assertEquals("Name must contain between 3 and 100 characters", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenNameIsTooLong() {

        String longName = "a".repeat(101);

        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> rigUser.validateName(longName));

        assertEquals("Name must contain between 3 and 100 characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ana", "Alejandro",
    "MaximilianicodemoDeLaSantisimaTrinidadJuanCarlosEustaquioGeronimoWenceslaoDeLosRiosYValenzuelaSanMar"})
    void shouldBeValidName(String name) {

        assertDoesNotThrow(() -> rigUser.validateName(name));
    }

    @Test
    void shouldThrowInvalidRigUserEmailExceptionWhenEmailIsNull() {

        String nullEmail = null;

        InvalidRigUserEmailException ex = assertThrows(
                InvalidRigUserEmailException.class,
                () -> rigUser.validateEmail(nullEmail));
        assertEquals("Invalid email", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserEmailExceptionWhenEmailIsBlank() {

        String blankEmail = "      ";

        InvalidRigUserEmailException ex = assertThrows(
                InvalidRigUserEmailException.class,
                () -> rigUser.validateEmail(blankEmail));
        assertEquals("Invalid email", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan", "juan@", "@gmail.com", "juan@gmail", "juan@gmail.", "juan@.com", "juan@@gmail.com", "juan gmail@test.com", "juan#gmail.com", "juan@test.c", "juan@test,com"})
    void shouldThrowInvalidRigUserEmailExceptionWhenEmailDoesNotMatchThePattern(String email) {

        InvalidRigUserEmailException ex = assertThrows(
                InvalidRigUserEmailException.class,
                () -> rigUser.validateEmail(email));
        assertEquals("Invalid e-mail format", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan@test.com", "bahamut@gmail.cl", "nombre.apellido@hotmail.com", "usuario123@empresa.org", "a+b@test.io", "user_name@test.net", "user-name@test.co.uk"})
    void shouldValidateEmailSuccessfully(String email) {

        assertDoesNotThrow(() -> rigUser.validateEmail(email));
    }

    @Test
    void shouldThrowInvalidRigUserPasswordExceptionWhenPasswordIsNull() {

        String nullPassword = null;

        InvalidRigUserPasswordException ex = assertThrows(
                InvalidRigUserPasswordException.class,
                () -> rigUser.validatePassword(nullPassword));
        assertEquals("Invalid password", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserPasswordExceptionWhenPasswordIsBlank() {

        String blankPassword = "         ";

        InvalidRigUserPasswordException ex = assertThrows(
                InvalidRigUserPasswordException.class,
                () -> rigUser.validatePassword(blankPassword));
        assertEquals("Invalid password", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"dsafhb/*v", "asi98"})
    void shouldThrowInvalidRigUserPasswordExceptionWhenPasswordIsTooShort(String password) {

        InvalidRigUserPasswordException ex = assertThrows(
                InvalidRigUserPasswordException.class,
                () -> rigUser.validatePassword(password));
        assertEquals("Password must contain at least 10 characters", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"dsafhb/*vx", "mrm390&%atrix697*"})
    void shouldValidatePasswordSuccessfully(String password) {

        assertDoesNotThrow(() -> rigUser.validatePassword(password));
    }

    @Test
    void shouldCreateRigUserWithConstructor() {

        Long id = 1L;
        String name = "Bahamut";
        String email = "bahamut@test.com";
        String password = "Password123";

        RigUser user = new RigUser(id, name, email, password);

        assertAll(
                () -> assertEquals(id, user.getId()),
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(email, user.getEmail().value()),
                () -> assertEquals(password, user.getPassword())
        );
    }

    @Test
    void shouldCreateRigUserWithEmptyConstructor() {

        Long id = 1L;
        String name = "Bahamut";
        String email = "bahamut@test.com";
        String password = "Password123";

        rigUser.setId(id);
        rigUser.setName(name);
        rigUser.setEmail(email);
        rigUser.setPassword(password);

        assertAll(
                () -> assertEquals(id, rigUser.getId()),
                () -> assertEquals(name, rigUser.getName()),
                () -> assertEquals(email, rigUser.getEmail().value()),
                () -> assertEquals(password, rigUser.getPassword())
        );
    }

    @Test
    void shouldCreateRigUserWithConstructorUsingEmailObject() {

        Long id = 1L;
        String name = "Bahamut";
        Email emailObj = new Email("bahamut@test.com");
        String password = "Password123";

        RigUser user = new RigUser(id, name, emailObj, password);

        assertAll(
                () -> assertEquals(id, user.getId()),
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(emailObj, user.getEmail()),
                () -> assertEquals(password, user.getPassword())
        );
    }

    @Test
    void shouldThrowInvalidRigUserNameExceptionWhenConstructorWithEmailObjectReceivesInvalidName() {

        Long id = 1L;
        String invalidName = "Ab";
        Email emailObj = new Email("bahamut@test.com");
        String password = "Password123";

        InvalidRigUserNameException exception = assertThrows(
                InvalidRigUserNameException.class,
                () -> new RigUser(id, invalidName, emailObj, password));

        assertEquals("Name must contain between 3 and 100 characters", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRigUserPasswordExceptionWhenConstructorWithEmailObjectReceivesInvalidPassword() {

        Long id = 1L;
        String name = "Bahamut";
        Email emailObj = new Email("bahamut@test.com");
        String invalidPassword = "short";

        InvalidRigUserPasswordException exception = assertThrows(
                InvalidRigUserPasswordException.class,
                () -> new RigUser(id, name, emailObj, invalidPassword));

        assertEquals("Password must contain at least 10 characters", exception.getMessage());
    }
}
