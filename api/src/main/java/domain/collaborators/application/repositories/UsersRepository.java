package domain.collaborators.application.repositories;


import domain.collaborators.enterprise.entities.User;

import java.util.List;


public abstract class UsersRepository {
    public abstract void create(User company);
    public abstract User findById(String id);
    public abstract User findByEmail(String email);
    public abstract List<User> findAllAdmin(String companyId);
    public abstract void save(User user);
}
