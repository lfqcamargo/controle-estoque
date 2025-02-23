package test.repositories;

import domain.collaborators.application.repositories.CompaniesRepository;
import domain.collaborators.enterprise.entities.Company;
import domain.collaborators.enterprise.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCompaniesRepository extends CompaniesRepository {

    private InMemoryUsersRepository usersRepository = new InMemoryUsersRepository();
    public List<Company> items = new ArrayList<>();

    public InMemoryCompaniesRepository(InMemoryUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void createCompanyAndUser(Company company, User user) {
        this.items.add(company);

        this.usersRepository.items.add(user);
    }

    public Company findByCnpj(String cnpj) {
        return this.items.stream()
                .filter(company -> company.getCnpj().equals(cnpj))
                .findFirst()
                .orElse(null);

    }
}
