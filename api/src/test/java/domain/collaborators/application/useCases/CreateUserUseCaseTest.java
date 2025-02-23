package test.java.domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import core.errors.NotFoundError;
import core.errors.NotPermissionError;
import domain.collaborators.application.dtos.CreateUserDTO;
import domain.collaborators.application.useCases.CreateUserUseCase;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;
import org.junit.Before;
import org.junit.Test;
import test.java.domain.collaborators.application.cryptography.HashGenerator;
import test.repositories.InMemoryUsersRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CreateUserUseCaseTest {

    private CreateUserUseCase sut;
    private InMemoryUsersRepository usersRepository;

    @Before
    public void setUp() {
        usersRepository = new InMemoryUsersRepository();
        HashGenerator hashGenerator = new HashGenerator();
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

        CreateUserDTO dto = new CreateUserDTO(user.getId(), name, email, password, role);

        sut.execute(dto);

        assertEquals(2, usersRepository.items.size());
        assertEquals(name, usersRepository.items.getLast().getName());
        assertEquals("123456-hashed", usersRepository.items.get(1).getPassword());
    }

    @Test(expected = NotFoundError.class)
    public void itShouldNotBePossibleToCreateWithANonExistentAuthenticatedUser() {
        UUID companyId = UUID.randomUUID();
        User user = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);

        usersRepository.items.add(user);

        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";
        UserRole role = UserRole.ADMIN;

        CreateUserDTO dto = new CreateUserDTO(UUID.randomUUID(), name, email, password, role);

        sut.execute(dto);
    }

    @Test(expected = NotPermissionError.class)
    public void itShouldNotBePossibleToCreateWithAnAuthenticatedUserWithoutPermission() {
        UUID companyId = UUID.randomUUID();
        User user = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(user);

        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";
        UserRole role = UserRole.ADMIN;

        CreateUserDTO dto = new CreateUserDTO(user.getId(), name, email, password, role);

        sut.execute(dto);
    }

    @Test(expected = AlreadyExistsError.class)
    public void itShouldNotBePossibleToCreateWithAnEmailAlreadyRegistered() {
        UUID companyId = UUID.randomUUID();
        User user = User.create("Lucas Camargo", "lfqcamargo@gmail.com", "a123456", UserRole.ADMIN, companyId);

        usersRepository.items.add(user);

        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";
        UserRole role = UserRole.ADMIN;

        CreateUserDTO dto = new CreateUserDTO(user.getId(), name, email, password, role);

        sut.execute(dto);
    }
}
