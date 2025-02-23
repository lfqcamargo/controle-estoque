package domain.collaborators.application.repositories;

import domain.collaborators.enterprise.entities.Company;
import domain.collaborators.enterprise.entities.User;

import java.util.ArrayList;

public abstract class CompaniesRepository {
    public abstract void createCompanyAndUser(Company company,User users);
    public abstract Company findByCnpj(String cnpj);
}
