package domain.collaborators.application.dtos;

import domain.collaborators.enterprise.entities.UserRole;

import java.util.UUID;

public class CreateUserRequestDTO {
    private final UUID userAuthenticatedId;
    private final String name;
    private final String email;
    private final String password;
    private final UserRole role;

    public CreateUserRequestDTO(UUID userAuthenticatedId, String name, String email, String password, UserRole role) {
        this.userAuthenticatedId = userAuthenticatedId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public UUID getUserAuthenticatedId() {
        return  this.userAuthenticatedId;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public UserRole getRole() { return this.role; }

}
