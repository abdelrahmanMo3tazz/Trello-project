package com.example.trello.model;
  

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    
    @Enumerated(EnumType.STRING)
    private Role  role; // Consider using an enum for roles
    // private Set<Board> boards = new HashSet<>();
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role  getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    //  public Set<Board> getBoards() {
    //     return boards;
    // }

    // public void setBoards(Set<Board> boards) {
    //     this.boards = boards;
    // }

    // // Methods to manage the many-to-many relationship
    // public void addBoard(Board board) {
    //     this.boards.add(board);
    //     // board.getCollaborators().add(this);
    // }

    // public void removeBoard(Board board) {
    //     this.boards.remove(board);
    //     // board.getCollaborators().remove(this);
    // }
} 