package riguser;

import domain.RigUser;
import exception.riguser.DuplicateRigUserException;
import exception.riguser.RigUserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.RigUserRepository;
import service.riguser.RigUserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestRigUserServiceImpl {

    @Mock
    private RigUserRepository repository;
    private List<RigUser> defaultUsers;
    private RigUser testSubject;

    @InjectMocks
    private RigUserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new RigUserServiceImpl(repository);

        defaultUsers = new ArrayList<>();
        defaultUsers.add(new RigUser(1L, "Alejandro Silva", "ale.silva@email.com", "pass1234"));
        defaultUsers.add(new RigUser(2L, "Beatriz Gómez", "beatriz.g@email.com", "secureB@2026"));

        testSubject = defaultUsers.get(1);
    }

    @Test
    void shouldCreateServiceSuccessFully() {
        //Act and assert
        assertNotNull(service);
    }

    @Test
    void shouldFindAllRigUsers() {
        //Arrange
        when(repository.getRigUsers()).thenReturn(defaultUsers);

        //Act
        List<RigUser> result = service.findAll();

        //Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(testSubject.getName(), result.get(1).getName())
        );
    }

    @Test
    void shouldFindRigUserById() {

        Long id = 2L;

        when(repository.getRigUserById(id)).thenReturn(testSubject);
        RigUser rigUser = service.findById(id);

        assertAll(
                () -> assertNotNull(rigUser),
                () -> assertSame(testSubject, rigUser)
        );

        verify(repository).getRigUserById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowRigUserNotFoundException() {
        //Arrange
        Long id = 5L;
        when(repository.getRigUserById(id)).thenReturn(null);

        //Act and assert
        RigUserNotFoundException ex = assertThrows(
                RigUserNotFoundException.class,
                () -> service.findById(id));
        assertEquals("User not found", ex.getMessage());
        verify(repository).getRigUserById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldExecuteValidationMethodsDuringSaveOperation() {

        // Arrange
        Long id = 1L;
        RigUser spyUser = spy(new RigUser(
                id,
                "Bahamut",
                "bahamut@test.com",
                "Password123"
        ));

        when(repository.getRigUserById(id)).thenReturn(null);
        when(repository.addRigUser(any(RigUser.class))).thenReturn(spyUser);

        // Act
        service.save(spyUser);

        // Assert
        verify(spyUser).validateEmail("bahamut@test.com");
        verify(spyUser).validateName("Bahamut");
        verify(spyUser).validatePassword("Password123");

        verify(repository).addRigUser(spyUser);
    }

    @Test
    void shouldThrowDuplicateRigUserException() {
        //Arrange
        Long id = 2L;
        when(repository.getRigUserById(id)).thenReturn(testSubject);

        //Act and assert
        DuplicateRigUserException ex = assertThrows(
                DuplicateRigUserException.class,
                ()-> service.save(testSubject));
        assertEquals("User already exists", ex.getMessage());
    }

    @Test
    void shouldUpdateRigUserSuccessfully() {
        // Arrange
        Long id = 2L;
        RigUser spyUser = spy(new RigUser(id, "Beatriz Modificado", "beatriz.mod@email.com", "secureB@2026"));
        when(repository.getRigUserById(id)).thenReturn(testSubject);
        when(repository.updateRigUser(id, spyUser)).thenReturn(spyUser);

        // Act
        RigUser updatedUser = service.update(id, spyUser);

        // Assert
        assertAll(
                () -> assertNotNull(updatedUser),
                () -> assertSame(spyUser, updatedUser)
        );

        verify(spyUser).validateEmail("beatriz.mod@email.com");
        verify(spyUser).validateName("Beatriz Modificado");
        verify(repository).getRigUserById(id);
        verify(repository).updateRigUser(id, spyUser);
    }

    @Test
    void shouldThrowRigUserNotFoundExceptionWhenUpdatingNonExistingUser() {
        //Arrange
        Long id = 5L;
        RigUser anyUser = new RigUser(id, "Carlos", "carlos@email.com", "pass1234");
        when(repository.getRigUserById(id)).thenReturn(null);

        // Act and Assert
        RigUserNotFoundException ex = assertThrows(
                RigUserNotFoundException.class,
                () -> service.update(id, anyUser)
        );

        assertEquals("User not found", ex.getMessage());
        verify(repository).getRigUserById(id);
        verify(repository, never()).updateRigUser(anyLong(), any(RigUser.class));
    }


    @Test
    void shouldDeleteRigUserSuccessfully() {
        // Arrange
        Long id = 2L;
        when(repository.getRigUserById(id)).thenReturn(testSubject);

        // Act and Assert
        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository).getRigUserById(id);
        verify(repository).deleteRigUser(id);
    }

    @Test
    void shouldThrowRigUserNotFoundExceptionWhenDeletingNonExistingUser() {
        // Arrange
        Long id = 5L;
        when(repository.getRigUserById(id)).thenReturn(null);

        // Act and Assert
        RigUserNotFoundException ex = assertThrows(
                RigUserNotFoundException.class,
                () -> service.deleteById(id)
        );

        assertEquals("User not found", ex.getMessage());

        verify(repository).getRigUserById(id);
        verify(repository, never()).deleteRigUser(anyLong());
    }
}