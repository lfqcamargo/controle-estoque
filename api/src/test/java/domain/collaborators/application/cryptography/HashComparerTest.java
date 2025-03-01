package domain.collaborators.application.cryptography;

public class HashComparerTest extends HashComparer {

    @Override
    public Boolean compare(String plain, String hash) {
        return plain.concat("-hashed").equals(hash);
    }
}
