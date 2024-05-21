package com.example.trello.controller;

import com.example.trello.model.dbList; 
import com.example.trello.service.ListService;
import com.example.trello.service.BoardService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @Autowired
    private BoardService boardService;

    /**
     * Creates a new list within a board.
     * @param boardId ID of the board to which the list will be added.
     * @param listName Name of the new list.
     * @param userId ID of the user performing the operation.
     * @return ResponseEntity with the created List or an error message.
     */
    @PostMapping("/")
    public ResponseEntity<?> createList(@RequestParam Long boardId, @RequestBody Map<String, String> requestBody, @RequestParam Long userId) {
        String listName = requestBody.get("listName");
        try {
            boardService.validateBoardOwnership(boardId, userId);
            dbList newList = listService.createList(boardId, listName, userId);
            return ResponseEntity.ok(newList);
        } catch (Exception e) {
            e.printStackTrace();  // Only for debugging, remove or use logging in production
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    

    /**
     * Deletes a list from a board.
     * @param listId ID of the list to be deleted.
     * @param userId ID of the user performing the operation.
     * @return ResponseEntity with success or error message.
     */
    @DeleteMapping("/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable Long listId, @RequestParam Long userId) {
        try {
            // Check if user is allowed to delete the list
            listService.deleteList(listId, userId);
            return ResponseEntity.ok("List deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
