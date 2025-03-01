package domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import core.errors.NotFoundError;
import core.errors.NotPermissionError;
import domain.collaborators.application.cryptography.HashGeneratorTest;
import domain.collaborators.application.dtos.CreateUserRequestDTO;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import domain.collaborators.application.cryptography.HashGenerator;
import repositories.InMemoryUsersRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateUserUseCaseTest {

    private CreateUserUseCase sut;
    private InMemoryUsersRepository usersRepository;

    @BeforeEach
    public void setUp() {
        usersRepository = new InMemoryUsersRepository();
        HashGenerator hashGenerator = new HashGeneratorTest();
        sut = new CreateUserUseCase(usersRepository, hashGenerator);
    }

    @Test
    public void shouldCreateUserSuccessfully() {
        UUID companyId = UUID.randomUUID();
        User user = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);

        usersRepository.items.add(user);

        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";
        UserRole role = UserRole.ADMIN;

        CreateUserRequestDTO dto = new CreateUserRequestDTO(user.getId(), name, email, password, role);

        sut.execute(dto);

        assertEquals(2, usersRepository.items.size());
        assertEquals(name, usersRepository.items.get(1).getName());
        assertEquals("123456-hashed", usersRepository.items.get(1).getPassword());
    }

    @Test
    public void itShouldNotBePossibleToCreateWithANonExistentAuthenticatedUser() {
        UUID companyId = UUID.randomUUID();
        User user = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);

        usersRepository.items.add(user);

        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";
        UserRole role = UserRole.ADMIN;

        CreateUserRequestDTO dto = new CreateUserRequestDTO(UUID.randomUUID(), name, email, password, role);

        assertThrows(NotFoundError.class, () -> sut.execute(dto));
    }

    @Test
    public void itShouldNotBePossibleToCreateWithAnAuthenticatedUserWithoutPermission() {
        UUID companyId = UUID.randomUUID();
        User user = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(user);

        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";
        UserRole role = UserRole.ADMIN;

        CreateUserRequestDTO dto = new CreateUserRequestDTO(user.getId(), name, email, password, role);

        assertThrows(NotPermissionError.class, () -> sut.execute(dto));
    }

    @Test
    public void itShouldNotBePossibleToCreateWithAnEmailAlreadyRegistered() {
        UUID companyId = UUID.randomUUID();
        User user = User.create("Lucas Camargo", "lfqcamargo@gmail.com", "a123456", UserRole.ADMIN, companyId);

        usersRepository.items.add(user);

        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";
        UserRole role = UserRole.ADMIN;

        CreateUserRequestDTO dto = new CreateUserRequestDTO(user.getId(), name, email, password, role);

        assertThrows(AlreadyExistsError.class, () -> sut.execute(dto));
    }
}
