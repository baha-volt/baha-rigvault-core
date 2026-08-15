package cl.bahatech.application.service;

import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.exception.DuplicateRigUserException;
import cl.bahatech.domain.exception.RigUserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.bahatech.domain.repository.RigUserRepository;
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
        defaultUsers.add(new RigUser(1L, "Alejandro Silva", "ale.silva@email.com", "pass123456"));
        defaultUsers.add(new RigUser(2L, "Beatriz Gómez", "beatriz.g@email.com", "secureB@2026"));

        testSubject = defaultUsers.get(1);
    }

    @Test
    void shouldCreateServiceSuccessFully() {

        assertNotNull(service);
    }

    @Test
    void shouldFindAllRigUsers() {

        when(repository.getRigUsers()).thenReturn(defaultUsers);

        List<RigUser> result = service.findAll();

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

        Long id = 5L;
        when(repository.getRigUserById(id)).thenReturn(null);

        RigUserNotFoundException ex = assertThrows(
                RigUserNotFoundException.class,
                () -> service.findById(id));
        assertEquals("User not found", ex.getMessage());
        verify(repository).getRigUserById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldSaveRigUserSuccessfullyWhenIdDoesNotExist() {

        Long id = 1L;
        RigUser newUser = new RigUser(id, "Bahamut", "bahamut@test.com", "Password123");

        when(repository.getRigUserById(id)).thenReturn(null);
        when(repository.addRigUser(newUser)).thenReturn(newUser);

        RigUser result = service.save(newUser);

        assertSame(newUser, result);
        verify(repository).getRigUserById(id);
        verify(repository).addRigUser(newUser);
    }

    @Test
    void shouldThrowDuplicateRigUserException() {

        Long id = 2L;
        when(repository.getRigUserById(id)).thenReturn(testSubject);

        DuplicateRigUserException ex = assertThrows(
                DuplicateRigUserException.class,
                ()-> service.save(testSubject));
        assertEquals("User already exists", ex.getMessage());
    }

    @Test
    void shouldUpdateRigUserSuccessfully() {

        Long id = 2L;
        RigUser updatedUserData = new RigUser(id, "Beatriz Modificado", "beatriz.mod@email.com", "secureB@2026");
        when(repository.getRigUserById(id)).thenReturn(testSubject);
        when(repository.updateRigUser(id, updatedUserData)).thenReturn(updatedUserData);

        RigUser updatedUser = service.update(id, updatedUserData);

        assertAll(
                () -> assertNotNull(updatedUser),
                () -> assertSame(updatedUserData, updatedUser)
        );

        verify(repository).getRigUserById(id);
        verify(repository).updateRigUser(id, updatedUserData);
    }

    @Test
    void shouldThrowRigUserNotFoundExceptionWhenUpdatingNonExistingUser() {

        Long id = 5L;
        RigUser anyUser = new RigUser(id, "Carlos", "carlos@email.com", "pass123456");
        when(repository.getRigUserById(id)).thenReturn(null);

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

        Long id = 2L;
        when(repository.getRigUserById(id)).thenReturn(testSubject);

        assertDoesNotThrow(() -> service.deleteById(id));
        verify(repository).getRigUserById(id);
        verify(repository).deleteRigUser(id);
    }

    @Test
    void shouldThrowRigUserNotFoundExceptionWhenDeletingNonExistingUser() {

        Long id = 5L;
        when(repository.getRigUserById(id)).thenReturn(null);

        RigUserNotFoundException ex = assertThrows(
                RigUserNotFoundException.class,
                () -> service.deleteById(id)
        );

        assertEquals("User not found", ex.getMessage());

        verify(repository).getRigUserById(id);
        verify(repository, never()).deleteRigUser(anyLong());
    }
}
