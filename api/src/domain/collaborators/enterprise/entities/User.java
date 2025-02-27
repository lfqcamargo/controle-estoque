package domain.collaborators.enterprise.entities;

import core.entities.Entity;

import java.util.Date;
import java.util.UUID;


public class User extends Entity {


    private final UUID id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private Boolean active;

    private final Date createdAt;
    private Date lastLogin;

    private final UUID companyId;

    private User(UUID id, String name, String email, String password, UserRole role, Boolean active, Date createdAt, Date lastLogin, UUID companyId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;

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

    public Boolean getActive() {
        return this.active;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setLastLogin() {
        this.lastLogin = new Date();
    }

    public void updateLastLogin() {
        this.lastLogin = new Date();
    }

    public Boolean isAdmin() {
        if (this.role == UserRole.ADMIN) {
            return true;
        }

        return false;
    }

    public static User create(String name, String email, String password, UserRole role, UUID companyId) {
        UUID id = UUID.randomUUID();
        Date createdAt = new Date();
        Boolean active = true;

        return new User(id, name, email, password, role, active, createdAt, null, companyId);
    }

    public static User create(UUID id, String name, String email, String password, UserRole role, Boolean active, Date createdAt, UUID companyId) {

        return new User(id, name, email, password, role, active, createdAt, null, companyId);
    }

    public static User create(UUID id, String name, String email, String password, UserRole role, Boolean active, Date createdAt, Date lastLogin, UUID companyId) {

        return new User(id, name, email, password, role, active, createdAt, lastLogin, companyId);
    }

}

