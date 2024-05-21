package com.example.trello.repository;

import com.example.trello.model.dbList; // Ensure to import your List model correctly

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ListRepository extends JpaRepository<dbList, Long> {
    // Custom query to find all lists by board ID
    List<dbList> findByBoardId(Long boardId);
}
