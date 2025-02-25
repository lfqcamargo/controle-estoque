package domain.collaborators.enterprise.entities;

import core.entities.Entity;

import java.util.Date;
import java.util.UUID;


public class User extends Entity {


    private final UUID id;
    private final String name;
    private final String email;
    private String password;
    private UserRole role;

    private final Date createdAt;
    private Date lastLogin;

    private final UUID companyId;

    private User(UUID id, String name, String email, String password, UserRole role, Date createdAt, Date lastLogin, UUID companyId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

        this.createdAt = createdAt;
        this.lastLogin = lastLogin;

        this.companyId = companyId;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return  this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public UserRole getRole() {
        return this.role;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getLastLogin() {
        return this.lastLogin;
    }

    public UUID getCompanyId() {
        return this.companyId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setLastLogin() {
        this.lastLogin = new Date();
    }

    public void updateLastLogin() {
        this.lastLogin = new Date();
    }

    public static User create(String name, String email, String password, UserRole role, UUID companyId) {
        UUID id = UUID.randomUUID();
        Date createdAt = new Date();

        return new User(id, name, email, password, role, createdAt, null, companyId);
    }

    public static User create(UUID id, String name, String email, String password, UserRole role, Date createdAt, UUID companyId) {

        return new User(id, name, email, password, role, createdAt, null, companyId);
    }

    public static User create(UUID id, String name, String email, String password, UserRole role, Date createdAt, Date lastLogin, UUID companyId) {

        return new User(id, name, email, password, role, createdAt, lastLogin, companyId);
    }

}

