package core.errors;

public class WrongCredentialsError extends Error {
    public WrongCredentialsError(String message) {
        super(message);
    }
}
