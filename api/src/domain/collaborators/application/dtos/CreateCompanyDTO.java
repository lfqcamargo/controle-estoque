package domain.collaborators.application.dtos;

import domain.collaborators.enterprise.entities.UserRole;

public class CreateCompanyDTO {
    private final String cnpj;
    private final String nameCompany;

    private final String nameUser;
    private final String email;
    private final String password;

    public CreateCompanyDTO(String cnpj, String nameCompany, String nameUser, String email, String password) {
        this.cnpj = cnpj;
        this.nameCompany = nameCompany;

        this.nameUser = nameUser;
        this.email = email;
        this.password = password;
    }

    public String getCnpj() {
        return cnpj;
    }
    public String getNameCompany() {
        return nameCompany;
    }

    public String getNameUser() {
        return this.nameUser;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }


}
