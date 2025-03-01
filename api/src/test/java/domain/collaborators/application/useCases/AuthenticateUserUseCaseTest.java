package domain.collaborators.application.useCases;

import domain.collaborators.application.cryptography.*;
import domain.collaborators.application.dtos.AuthenticatedUserResponseDTO;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import repositories.InMemoryUsersRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticateUserUseCaseTest {

    private InMemoryUsersRepository usersRepository;
    private AuthenticateUserUseCase sut;


    @BeforeEach
    public void setUp() {
        usersRepository = new InMemoryUsersRepository();
        HashComparer hashComparer = new HashComparerTest();
        Encrypter encrypter = new EncrypterTest();


        sut = new AuthenticateUserUseCase(usersRepository, hashComparer, encrypter);
    }

    @Test
    public void shouldAuthenticatedSuccessfully() {
        UUID companyId = UUID.randomUUID();
        String name = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";

        HashGenerator hashGenerator = new HashGeneratorTest();

        String hashedPassword = hashGenerator.hash(password);

        User user = User.create(name, email, hashedPassword, UserRole.ADMIN, companyId);

        usersRepository.create(user);

        AuthenticatedUserResponseDTO accessToken = sut.execute(email, password);

        Encrypter encrypter = new EncrypterTest();
        String idEncrypter = encrypter.encrypt(user.getId().toString());


        assertEquals(accessToken.getSub(), idEncrypter);
    }

}
