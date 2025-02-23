package test.java.domain.collaborators.application.cryptography;

public class HashComparer extends domain.collaborators.application.cryptography.HashComparer {

    public Boolean compare(String plain, String hash) {
        return plain.concat("-hashed").equals(hash);
    }
}
