package domain.collaborators.application.useCases;

import core.errors.AlreadyExistsError;
import domain.collaborators.application.cryptography.HashGenerator;
import domain.collaborators.application.dtos.CreateCompanyRequestDTO;
import domain.collaborators.application.repositories.CompaniesRepository;
import domain.collaborators.application.repositories.UsersRepository;
import domain.collaborators.enterprise.entities.Company;
import domain.collaborators.enterprise.entities.User;
import domain.collaborators.enterprise.entities.UserRole;

public class CreateCompanyAndUserUseCase {

    private final CompaniesRepository companiesRepository;
    private final UsersRepository usersRepository;
    private final HashGenerator hashGenerator;

    public CreateCompanyAndUserUseCase(CompaniesRepository companiesRepository, UsersRepository usersRepository, HashGenerator hashGenerator) {
        this.companiesRepository = companiesRepository;
        this.usersRepository = usersRepository;
        this.hashGenerator = hashGenerator;
    }


    public void execute(CreateCompanyRequestDTO props) {
        Company companyCnpj = this.companiesRepository.findByCnpj(props.getCnpj());

        if (companyCnpj != null) {
            throw new AlreadyExistsError("Company already exists.");
        }

        User userEmail = this.usersRepository.findByEmail(props.getEmail());

        if (userEmail != null) {
            throw new AlreadyExistsError("Email already exists.");
        }

        Company company = Company.create(props.getCnpj(), props.getNameCompany());
        String hashedPassword = this.hashGenerator.hash(props.getPassword());
        User user = User.create(props.getNameUser(), props.getEmail(), hashedPassword, UserRole.ADMIN, company.getId());

        this.companiesRepository.createCompanyAndUser(company, user);
    }

}
