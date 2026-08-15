package cl.bahatech.application.usecase;

import cl.bahatech.domain.entity.RigUser;
import cl.bahatech.domain.exception.DuplicateRigUserException;
import cl.bahatech.domain.repository.RigUserRepository;
import cl.bahatech.application.port.MessageNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestRegisterRigUserUseCase {

    @Mock
    private RigUserRepository repository;

    @Mock
    private MessageNotifier messageNotifier;

    private RegisterRigUserUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegisterRigUserUseCase(repository, messageNotifier);
    }

    @Test
    void shouldCreateUseCaseSuccessfully() {
        assertNotNull(useCase);
    }

    @Test
    void shouldRegisterRigUserSuccessfullyWhenIdDoesNotExist() {
        Long id = 1L;
        RigUser newUser = new RigUser(id, "Bahamut", "bahamut@test.com", "Password123");

        when(repository.getRigUserById(id)).thenReturn(null);
        when(repository.addRigUser(newUser)).thenReturn(newUser);

        RigUser result = useCase.execute(newUser);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(newUser.getId(), result.getId()),
                () -> assertEquals(newUser.getName(), result.getName())
        );

        verify(repository).getRigUserById(id);
        verify(repository).addRigUser(newUser);
        verify(messageNotifier).notify("User registered successfully: " + newUser.getName());
        verifyNoMoreInteractions(repository, messageNotifier);
    }

    @Test
    void shouldThrowDuplicateRigUserExceptionWhenIdAlreadyExists() {
        Long id = 1L;
        RigUser newUser = new RigUser(id, "Bahamut", "bahamut@test.com", "Password123");

        when(repository.getRigUserById(id)).thenReturn(newUser);

        DuplicateRigUserException ex = assertThrows(
                DuplicateRigUserException.class,
                () -> useCase.execute(newUser)
        );

        assertEquals("RigUser already exists", ex.getMessage());

        verify(repository).getRigUserById(id);
        verifyNoInteractions(messageNotifier);
        verifyNoMoreInteractions(repository);
    }
}
