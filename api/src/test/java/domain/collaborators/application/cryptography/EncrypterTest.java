package domain.collaborators.application.cryptography;
import java.util.Base64;

public class EncrypterTest extends Encrypter {

    @Override
    public String encrypt(String payload) {
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

}
