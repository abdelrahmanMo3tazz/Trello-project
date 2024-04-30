package repository;

import model.User;

public interface UserRepository {
    User save(User user);
    User findByEmail(String email);
}
