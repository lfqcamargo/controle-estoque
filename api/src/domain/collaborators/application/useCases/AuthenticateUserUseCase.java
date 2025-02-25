package domain.collaborators.application.useCases;

import core.errors.WrongCredentialsError;
import domain.collaborators.application.cryptography.Encrypter;
import domain.collaborators.application.cryptography.HashComparer;
import domain.collaborators.application.dtos.AuthenticatedUserResponseDTO;
import domain.collaborators.application.repositories.UsersRepository;
import domain.collaborators.enterprise.entities.User;

public class AuthenticateUserUseCase {

    private final UsersRepository usersRepository;
    private final HashComparer hashComparer;
    private final Encrypter encrypter;

    public AuthenticateUserUseCase(UsersRepository usersRepository, HashComparer hashComparer, Encrypter encrypter) {
        this.usersRepository = usersRepository;
        this.hashComparer = hashComparer;
        this.encrypter = encrypter;
    }

    public AuthenticatedUserResponseDTO execute(String email, String password) {
        User user = this.usersRepository.findByEmail(email);

        if (user == null) {
            throw new WrongCredentialsError("Credentials are not valid.");
        }

        Boolean isPasswordValid = this.hashComparer.compare(password, user.getPassword());

        if (!isPasswordValid) {
            throw new WrongCredentialsError("Credentials are not valid.");
        }

        user.updateLastLogin();
        this.usersRepository.save(user);

        String encryptedUserId = this.encrypter.encrypt(user.getId().toString());

        return new AuthenticatedUserResponseDTO(encryptedUserId);
    }
}
