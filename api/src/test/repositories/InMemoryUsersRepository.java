package test.repositories;

import domain.collaborators.application.repositories.UsersRepository;
import domain.collaborators.enterprise.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUsersRepository extends UsersRepository {
    public List<User> items = new ArrayList<>();

    public void create(User User) {
        this.items.add(User);
    }

    public User findById(String id) {
        return this.items.stream()
                .filter(User -> User.getId().toString().equals(id))
                .findFirst()
                .orElse(null);

    }

    public User findByEmail(String email) {
        return this.items.stream()
                .filter(User -> User.getEmail().equals(email))
                .findFirst()
                .orElse(null);

    }
}
