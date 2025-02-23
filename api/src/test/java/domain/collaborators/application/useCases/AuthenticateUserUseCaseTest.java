package test.java.domain.collaborators.application.useCases;

import test.java.domain.collaborators.application.cryptography.Encrypter;
import test.java.domain.collaborators.application.cryptography.HashComparer;
import test.java.domain.collaborators.application.cryptography.HashGenerator;
import domain.collaborators.application.repositories.UsersRepository;
import domain.collaborators.application.useCases.AuthenticateUserUseCase;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;
import org.junit.Before;
import org.junit.Test;
import test.repositories.InMemoryUsersRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AuthenticateUserUseCaseTest {

    private InMemoryUsersRepository usersRepository;
    private HashComparer hashComparer;
    private Encrypter encrypter;
    private AuthenticateUserUseCase sut;


    @Before
    public void setUp() {
        usersRepository = new InMemoryUsersRepository();
        HashComparer hashComparer = new HashComparer();
        Encrypter encrypter = new Encrypter();

        sut = new AuthenticateUserUseCase(usersRepository, hashComparer, encrypter);
    }

    @Test
    public void shouldAuthenticatedSuccessfully() {
        UUID companyId = UUID.randomUUID();
        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";

        HashGenerator hashGenerator = new HashGenerator();

        String hashedPassword = hashGenerator.hash(password);

        User user = User.create(name, email, hashedPassword, UserRole.ADMIN, companyId);

        usersRepository.create(user);

        sut.execute(email, password);

    }

}
