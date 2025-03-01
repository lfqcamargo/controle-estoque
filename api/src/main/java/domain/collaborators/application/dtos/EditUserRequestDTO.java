package domain.collaborators.application.dtos;

import domain.collaborators.enterprise.entities.UserRole;

import java.util.UUID;

public class EditUserRequestDTO {
    private final UUID userAuthenticatedId;
    private final UUID userEditedId;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private Boolean active;

    public EditUserRequestDTO(UUID userAuthenticatedId, UUID userEditedId) {
        this.userAuthenticatedId = userAuthenticatedId;
        this.userEditedId = userEditedId;
    }

    public UUID getUserAuthenticatedId() {
        return userAuthenticatedId;
    }

    public UUID getUserEditedId() {
        return userEditedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
