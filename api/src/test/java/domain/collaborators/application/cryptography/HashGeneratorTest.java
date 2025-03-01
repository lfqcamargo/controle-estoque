package domain.collaborators.application.cryptography;

public class HashGeneratorTest extends HashGenerator {

    @Override
    public String hash(String plain) {
        return plain.concat("-hashed");
    }

}
