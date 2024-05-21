package com.example.trello.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.trello.model.User;
import com.example.trello.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registered = userService.registerUser(user);
        return ResponseEntity.ok(registered);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        User user = userService.loginUser(credentials.get("email"), credentials.get("password"));
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response); // Successfully authenticated
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed"); // Authentication failed
    }
}
