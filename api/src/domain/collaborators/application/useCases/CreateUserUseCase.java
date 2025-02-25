package domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import core.errors.NotFoundError;
import core.errors.NotPermissionError;
import domain.collaborators.application.dtos.CreateUserRequestDTO;
import domain.collaborators.application.repositories.UsersRepository;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;
import domain.collaborators.application.cryptography.HashGenerator;

import java.util.UUID;

public class CreateUserUseCase {

    private final UsersRepository usersRepository;
    private final HashGenerator hashGenerator;

    public CreateUserUseCase(UsersRepository usersRepository, HashGenerator hashGenerator) {
        this.usersRepository = usersRepository;
        this.hashGenerator = hashGenerator;
    }

    public void execute(CreateUserRequestDTO props) {

        User userAuthenticated = checkAuthenticatedUser(props.getUserAuthenticatedId());

        User userEmail = this.usersRepository.findByEmail(props.getEmail());

        if (userEmail != null) {
            throw new AlreadyExistsError("Email already exists.");
        }

        String hashedPassword = this.hashGenerator.hash(props.getPassword());

        User user = User.create(props.getName(),props.getEmail(), hashedPassword, props.getRole(), userAuthenticated.getCompanyId());

        this.usersRepository.create(user);

    }

    private User checkAuthenticatedUser(UUID id) {
        User userAuthenticated = this.usersRepository.findById((id.toString()));

        if (userAuthenticated == null) {
            throw new NotFoundError("User authenticated not found.");
        }

        if (userAuthenticated.getRole() != UserRole.ADMIN) {
            throw new NotPermissionError("User not permissions");
        }

        return userAuthenticated;
    }

}
