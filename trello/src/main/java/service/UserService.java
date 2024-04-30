package service;

import model.User;

public interface UserService {
    User registerUser(User user);
    User loginUser(String email, String password);
}
