package domain.collaborators.application.dtos;

public class AuthenticatedUserResponseDTO {
    private final String sub;

    public AuthenticatedUserResponseDTO(String sub) {
        this.sub = sub;
    }

    public String getSub() {
        return sub;
    }
}
