package test.java.domain.collaborators.application.cryptography;
import java.util.Base64;

public class Encrypter extends domain.collaborators.application.cryptography.Encrypter {
    public String encrypt(String payload) {
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

}
