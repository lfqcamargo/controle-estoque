package domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import domain.collaborators.application.cryptography.HashGenerator;
import domain.collaborators.application.cryptography.HashGeneratorTest;
import domain.collaborators.application.dtos.CreateCompanyRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.InMemoryCompaniesRepository;
import repositories.InMemoryUsersRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CreateCompanyAndUserUseCaseTest {

    private CreateCompanyAndUserUseCase sut;
    private InMemoryCompaniesRepository companiesRepository;

    @BeforeEach
    public void setUp() {
        HashGenerator hashGeneratorTest = new HashGeneratorTest();
        InMemoryUsersRepository usersRepository = new InMemoryUsersRepository();
        companiesRepository = new InMemoryCompaniesRepository(usersRepository);

        sut = new CreateCompanyAndUserUseCase(companiesRepository, usersRepository, hashGeneratorTest);
    }

    @Test
    public void shouldCreateCompanySuccessfully() {
        String cnpj = "123456";
        String nameCompany = "lfqcamargo@gmail.com";

        String nameUser = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";

        CreateCompanyRequestDTO dto = new CreateCompanyRequestDTO(cnpj, nameCompany, nameUser, email, password);

        sut.execute(dto);

        assertEquals(1, companiesRepository.items.size());
        assertEquals(nameCompany, companiesRepository.items.get(0).getName());
    }

    @Test
    public void itShouldNotBePossibleToCreateCompanyWithTheSameCnpj() {

        String cnpj = "123456";
        String nameCompany = "lfqcamargo@gmail.com";

        String nameUser = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";

        CreateCompanyRequestDTO dto1 = new CreateCompanyRequestDTO(cnpj, nameCompany, nameUser, email, password);
        CreateCompanyRequestDTO dto2 = new CreateCompanyRequestDTO(cnpj, nameCompany, nameUser, "email", password);

        sut.execute(dto1);

        assertThrows(AlreadyExistsError.class, () -> sut.execute(dto2));
    }

    @Test
    public void itShouldNotBePossibleToCreateCompanyWithUserEmailTheSameEmail() {
        String cnpj = "123456";
        String nameCompany = "lfqcamargo@gmail.com";

        String nameUser = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";

        CreateCompanyRequestDTO dto1 = new CreateCompanyRequestDTO(cnpj, nameCompany, nameUser, email, password);
        CreateCompanyRequestDTO dto2 = new CreateCompanyRequestDTO("12345", nameCompany, nameUser, email, password);

        sut.execute(dto1);

        assertThrows(AlreadyExistsError.class, () -> sut.execute(dto2));
    }
}
