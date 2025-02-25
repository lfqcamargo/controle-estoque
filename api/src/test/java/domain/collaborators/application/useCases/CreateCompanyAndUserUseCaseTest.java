package test.java.domain.collaborators.application.useCases;
import static org.junit.Assert.*;

import domain.collaborators.application.dtos.CreateCompanyRequestDTO;
import domain.collaborators.application.useCases.CreateCompanyAndUserUseCase;
import core.errors.AlreadyExistsError;
import org.junit.Before;
import org.junit.Test;
import test.java.domain.collaborators.application.cryptography.HashGenerator;
import test.repositories.InMemoryCompaniesRepository;
import test.repositories.InMemoryUsersRepository;

public class CreateCompanyAndUserUseCaseTest {

    private CreateCompanyAndUserUseCase sut;
    private InMemoryCompaniesRepository companiesRepository;

    @Before
    public void setUp() {
        HashGenerator hashGenerator = new HashGenerator();
        InMemoryUsersRepository usersRepository = new InMemoryUsersRepository();
        companiesRepository = new InMemoryCompaniesRepository(usersRepository);

        sut = new CreateCompanyAndUserUseCase(companiesRepository, usersRepository, hashGenerator);
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
        assertEquals(nameCompany, companiesRepository.items.getFirst().getName());
    }

    @Test(expected = AlreadyExistsError.class)
    public void itShouldNotBePossibleToCreateCompanyWithTheSameCnpj() {
        String cnpj = "123456";
        String nameCompany = "lfqcamargo@gmail.com";

        String nameUser = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";

        CreateCompanyRequestDTO dto = new CreateCompanyRequestDTO(cnpj, nameCompany, nameUser, email, password);

        sut.execute(dto);
        sut.execute(dto);
    }

    @Test(expected = AlreadyExistsError.class)
    public void itShouldNotBePossibleToCreateCompanyWithUserEmailTheSameEmail() {
        String cnpj = "123456";
        String nameCompany = "lfqcamargo@gmail.com";

        String nameUser = "Lucas Camargo";
        String email = "lfqcamargo@gmail.com";
        String password = "123456";

        CreateCompanyRequestDTO dto1 = new CreateCompanyRequestDTO(cnpj, nameCompany, nameUser, email, password);
        CreateCompanyRequestDTO dto2 = new CreateCompanyRequestDTO("12345", nameCompany, nameUser, email, password);

        sut.execute(dto1);
        sut.execute(dto2);
    }
}
