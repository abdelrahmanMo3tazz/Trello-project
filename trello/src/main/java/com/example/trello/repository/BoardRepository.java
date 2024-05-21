package com.example.trello.repository;

import com.example.trello.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByName(String name); // Check if a board with the given name exists
    List<Board> findAllByCreatorId(Long creatorId); // Find all boards created by a specific user
     // Method to find a board by its ID
     Optional<Board> findById(Long id);
}
