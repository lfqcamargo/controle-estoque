package domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import core.errors.NotAllowed;
import core.errors.NotFoundError;
import core.errors.NotPermissionError;
import domain.collaborators.application.cryptography.HashGeneratorTest;
import domain.collaborators.application.dtos.EditUserRequestDTO;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import domain.collaborators.application.cryptography.HashGenerator;
import repositories.InMemoryUsersRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditUserUseCaseTest {

    private EditUserUseCase sut;
    private InMemoryUsersRepository usersRepository;

    @BeforeEach
    public void setUp() {
        usersRepository = new InMemoryUsersRepository();
        HashGenerator hashGenerator = new HashGeneratorTest();
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
        assertEquals("Quinato de Camargo", usersRepository.items.get(1).getName());
        assertEquals("daro.drakoss@hotmail.com", usersRepository.items.get(1).getEmail());
        assertEquals(UserRole.ADMIN, usersRepository.items.get(1).getRole());
        assertEquals("123456-hashed", usersRepository.items.get(1).getPassword());
        assertEquals(false, usersRepository.items.get(1).getActive());
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
        assertEquals("Quinato de Camargo", usersRepository.items.get(1).getName());
        assertEquals("daro.drakoss@hotmail.com", usersRepository.items.get(1).getEmail());
        assertEquals("123456-hashed", usersRepository.items.get(1).getPassword());
    }

    @Test
    public void itShouldNotBePossibleToEditAnEmailThatAlreadyHasARegistration() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userAuthenticated.getId(), userEdit.getId());
        dto.setEmail("lfqcamargo@gmail.com.br");

        assertThrows(AlreadyExistsError.class, () -> sut.execute(dto));

        assertEquals(2, usersRepository.items.size());
    }

    @Test
    public void itShouldNotBePossibleToEditWithTheNonExistentAuthenticatedUser() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(UUID.randomUUID(), userEdit.getId());
        dto.setEmail("lfqcamargo@gmail.com.br");


        assertThrows(NotFoundError.class, () -> sut.execute(dto));

        assertEquals(2, usersRepository.items.size());
    }

    @Test
    public void itShouldNotBePossibleToEditWithTheNonExistentEditedUser() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.ADMIN, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userAuthenticated.getId(), UUID.randomUUID());
        dto.setEmail("lfqcamargo@gmail.com.br");


        assertThrows(NotFoundError.class, () -> sut.execute(dto));

        assertEquals(2, usersRepository.items.size());
    }

    @Test
    public void itShouldNotBePossibleToEditUserIfNotAuthenticatedOrAdmin() {
        UUID companyId = UUID.randomUUID();
        User userAuthenticated = User.create("Lucas Camargo", "lfqcamargo@gmail.com.br", "a123456", UserRole.USER, companyId);
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userAuthenticated);
        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userAuthenticated.getId(), userEdit.getId());
        dto.setEmail("lfqcamargo@gmail.com.br");

        assertThrows(NotPermissionError.class, () -> sut.execute(dto));
    }

    @Test
    public void itShouldNotChangeRoleIfNotAdmin() {
        UUID companyId = UUID.randomUUID();
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.USER, companyId);

        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userEdit.getId(), userEdit.getId());
        dto.setRole(UserRole.ADMIN);

        assertThrows(NotPermissionError.class, () -> sut.execute(dto));
    }

    @Test
    public void itShouldNotChangeOwnRole() {
        UUID companyId = UUID.randomUUID();
        User userEdit = User.create("Camargo Lucas", "daro.drakoss@hotmail.com", "a123456", UserRole.ADMIN, companyId);

        usersRepository.items.add(userEdit);

        EditUserRequestDTO dto = new EditUserRequestDTO(userEdit.getId(), userEdit.getId());
        dto.setRole(UserRole.USER);

        assertThrows(NotAllowed.class, () -> sut.execute(dto));
    }

}
