package domain.collaborators.enterprise.entities;

import core.entities.Entity;

import java.util.Date;
import java.util.UUID;

public class Company extends Entity {

    private final UUID id;
    private final String cnpj;
    private final String name;
    private final Date createdAt;

    private Company(UUID id, String cnpj, String name, Date createdAt) {
        this.id = id;
        this.cnpj = cnpj;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return this.id;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public String getName() {
        return this.name;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public static Company create(String cnpj, String name) {
        UUID id = UUID.randomUUID();
        Date createdAt = new Date();

        return new Company(id, cnpj, name, createdAt);
    }

    public static Company create(UUID id, String cnpj, String name, Date createdAt) {

        return new Company(id, cnpj, name, createdAt);
    }

}
