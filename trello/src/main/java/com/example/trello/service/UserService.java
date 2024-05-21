package com.example.trello.service;

import com.example.trello.model.User;
import com.example.trello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to register a new user
    public User registerUser(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new RuntimeException("User already exists with email: " + newUser.getEmail());
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    // Method to update user details
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

     // Updated method to authenticate a user and return the User object
     public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Return the user object if authentication is successful
        }
        return null; // Return null if authentication fails
    }
  // Method to find a user by their ID
  public User findById(Long id) throws Exception {
    return userRepository.findById(id)
            .orElseThrow(() -> new Exception("User not found with id: " + id));
}
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}
