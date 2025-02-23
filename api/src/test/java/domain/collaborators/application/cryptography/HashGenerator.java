package test.java.domain.collaborators.application.cryptography;

public class HashGenerator extends domain.collaborators.application.cryptography.HashGenerator {

    public String hash(String plain) {
        return plain.concat("-hashed");
    }

    public Boolean compare(String plain, String hash) {
        return plain.concat("-hashed").equals(hash);
    }
}
