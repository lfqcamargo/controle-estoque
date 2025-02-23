package domain.collaborators.application.cryptography;

public abstract class HashComparer {
   public abstract Boolean compare(String plain, String hash);
}
