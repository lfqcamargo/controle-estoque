package domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import core.errors.NotAllowed;
import core.errors.NotFoundError;
import core.errors.NotPermissionError;
import domain.collaborators.application.cryptography.HashGenerator;
import domain.collaborators.application.dtos.EditUserRequestDTO;
import domain.collaborators.application.repositories.UsersRepository;
import domain.collaborators.enterprise.entities.User;

public class EditUserUseCase {

    private final UsersRepository usersRepository;
    private final HashGenerator hashGenerator;

    public EditUserUseCase(UsersRepository usersRepository, HashGenerator hashGenerator) {
        this.usersRepository = usersRepository;
        this.hashGenerator = hashGenerator;
    }

    public void execute(EditUserRequestDTO props) {
        User userAuthenticated = this.usersRepository.findById(props.getUserAuthenticatedId().toString());
        User userEdited = this.usersRepository.findById(props.getUserEditedId().toString());
        this.checkUser(userAuthenticated, userEdited);

        if (props.getName() != null) {
            userEdited.setName(props.getName());
        }

        if (props.getEmail() != null) {
            User userEmail = this.usersRepository.findByEmail(props.getEmail());

            if (userEmail != null) {
               throw new AlreadyExistsError("Email already exists.");
            }

            userEdited.setEmail(props.getEmail());
        }

        if (props.getPassword() != null) {
            String hashedPassword = this.hashGenerator.hash(props.getPassword());

            userEdited.setPassword(hashedPassword);
        }

        if (props.getRole() != null) {
            this.checkRulesRole(userAuthenticated, userEdited);

            userEdited.setRole(props.getRole());
        }

        if (props.getActive() != null) {
            if (props.getUserAuthenticatedId().equals(props.getUserEditedId())) {
                throw new NotAllowed("User cannot deactivate.");
            }

            userEdited.setActive(props.getActive());
        }

    }

    private void checkUser(User userAuthenticated, User userEdited) {
        if (userAuthenticated == null) {
            throw new NotFoundError("User authenticated not found.");
        }

        if (userEdited == null) {
            throw new NotFoundError("User edited not found.");
        }

        if (!userAuthenticated.isAdmin() & !userAuthenticated.equals(userEdited)) {
            throw new NotPermissionError("User not permissions.");
        }
    }

    private void checkRulesRole(User userAuthenticated, User userEdited) {
        if (!userAuthenticated.isAdmin()) {
            throw new NotPermissionError("User not permissions.");
        }

        if (userAuthenticated.equals(userEdited)) {
            throw new NotAllowed("The user cannot change their own role.");
        }
    }

}
