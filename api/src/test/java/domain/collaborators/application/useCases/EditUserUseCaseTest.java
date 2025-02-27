package test.java.domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import core.errors.NotAllowed;
import core.errors.NotFoundError;
import core.errors.NotPermissionError;
import domain.collaborators.application.dtos.EditUserRequestDTO;
import domain.collaborators.application.useCases.EditUserUseCase;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;
import org.junit.Before;
import org.junit.Test;
import test.java.domain.collaborators.application.cryptography.HashGenerator;
import test.repositories.InMemoryUsersRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EditUserUseCaseTest {

    private EditUserUseCase sut;
    private InMemoryUsersRepository usersRepository;

    @Before
    public void setUp() {
        usersRepository = new InMemoryUsersRepository();
        HashGenerator hashGenerator = new HashGenerator();
        sut = new EditUserUseCase(usersRepository, hashGenerator);
    }

    @Test
    public void shouldEditUserSuccessfullyIfYouAreAdmin() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "lfqcamargo@gmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userAuthenticated.getId(), userEdit.getId());
        dto.setName("Quinato de Camargo");
        dto.setEmail("daro.drakoss@hotmail.com");
        dto.setRole(UserRole.ADMIN);
        dto.setPassword("123456");
        dto.setActive(false);

        sut.execute(dto);

        assertEquals(2, usersRepository.items.size());
        assertEquals("Quinato de Camargo", usersRepository.items.getLast().getName());
        assertEquals("daro.drakoss@hotmail.com", usersRepository.items.getLast().getEmail());
        assertEquals(UserRole.ADMIN, usersRepository.items.getLast().getRole());
        assertEquals("123456-hashed", usersRepository.items.getLast().getPassword());
        assertEquals(false, usersRepository.items.getLast().getActive());
    }

    @Test
    public void shouldEditUserSuccessfullyIfYouAreTheAuthenticatedUser() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "lfqcamargo@gmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userEdit.getId(), userEdit.getId());
        dto.setName("Quinato de Camargo");
        dto.setEmail("daro.drakoss@hotmail.com");
        dto.setPassword("123456");

        sut.execute(dto);

        assertEquals(2, usersRepository.items.size());
        assertEquals("Quinato de Camargo", usersRepository.items.getLast().getName());
        assertEquals("daro.drakoss@hotmail.com", usersRepository.items.getLast().getEmail());
        assertEquals("123456-hashed", usersRepository.items.getLast().getPassword());
    }

    @Test(expected = AlreadyExistsError.class)
    public void itShouldNotBePossibleToEditAnEmailThatAlreadyHasARegistration() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userAuthenticated.getId(), userEdit.getId());
        dto.setEmail("lfqcamargo@gmail.com.br");

        sut.execute(dto);

        assertEquals(2, usersRepository.items.size());
    }

    @Test(expected = NotFoundError.class)
    public void itShouldNotBePossibleToEditWithTheNonExistentAuthenticatedUser() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(UUID.randomUUID(), userEdit.getId());
        dto.setEmail("lfqcamargo@gmail.com.br");

        sut.execute(dto);

        assertEquals(2, usersRepository.items.size());
    }

    @Test(expected = NotFoundError.class)
    public void itShouldNotBePossibleToEditWithTheNonExistentEditedUser() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userAuthenticated.getId(), UUID.randomUUID());
        dto.setEmail("lfqcamargo@gmail.com.br");

        sut.execute(dto);

        assertEquals(2, usersRepository.items.size());
    }

    @Test(expected = NotPermissionError.class)
    public void itShouldNotBePossibleToEditUserIfNotAuthenticatedOrAdmin() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.USER, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userAuthenticated.getId(), userEdit.getId());
        dto.setEmail("lfqcamargo@gmail.com.br");

        sut.execute(dto);
    }

    @Test(expected = NotPermissionError.class)
    public void itShouldNotChangeRoleIfNotAdmin() {
        UUID companyId = UUID.randomUUID();
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userEdit.getId(), userEdit.getId());
        dto.setRole(UserRole.ADMIN);

        sut.execute(dto);
    }

    @Test(expected = NotAllowed.class)
    public void itShouldNotChangeOwnRole() {
        UUID companyId = UUID.randomUUID();
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.ADMIN, companyId);

        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userEdit.getId(), userEdit.getId());
        dto.setRole(UserRole.USER);

        sut.execute(dto);
    }

}
