package com.example.trello.dto;

import com.example.trello.model.Role;

/**
 * Data Transfer Object for User information.
 */
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private Role role; // Assuming role is a simple String. Adjust according to your setup, possibly using an Enum.

    // Constructors
    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Optional: Override toString, equals, and hashCode methods as needed for debugging and usage within collections.

    @Override
    public String toString() {
        return "UserDTO{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
}
