package com.example.trello.controller;

import com.example.trello.dto.UserDTO;
import com.example.trello.model.Board;
import com.example.trello.model.Role;
import com.example.trello.model.User;
import com.example.trello.service.BoardService;
import com.example.trello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    /**
     * Create a new board. Access restricted to TeamLeaders.
     * 
     * @param board  Board data from request body.
     * @param userId ID of the user making the request.
     * @return ResponseEntity with the created board or error message.
     */
    @PostMapping("/")
    public ResponseEntity<?> createBoard(@RequestBody Board board, @RequestParam Long userId) {
        try {
            User user = userService.findById(userId);
            if (user.getRole() != Role.TEAM_LEADER) {
                return new ResponseEntity<>("Only team leaders can create boards.", HttpStatus.FORBIDDEN);
            }
            Board newBoard = boardService.createBoard(board, user.getId());
            return ResponseEntity.ok(newBoard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Only Tram leader Can Create Boards");
        }
    }

    /**
     * Get all boards created by a specific user.
     * 
     * @param userId ID of the user making the request.
     * @return ResponseEntity with list of boards.
     */
    @GetMapping("/")
    public ResponseEntity<?> getAllBoards(@RequestParam Long userId) {
        try {
            User user = userService.findById(userId);
            List<Board> boards = boardService.findAllBoardsByUser(user.getId());
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            // Log the exception details for debugging
            // Logger.log(Level.ERROR, "Error fetching boards for user ID: " + userId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + userId);
        }
    }

    /**
     * Delete a board. Only accessible by the board's creator.
     * 
     * @param boardId ID of the board to delete.
     * @param userId  ID of the user making the request.
     * @return ResponseEntity with success or error message.
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @RequestParam Long userId) {
        try {
            User currentUser = userService.findById(userId);
            if (!currentUser.getId().equals(boardService.findBoardById(boardId).getCreator().getId())) {
                return new ResponseEntity<>("Only the creator can delete the board.", HttpStatus.FORBIDDEN);
            }
            boardService.deleteBoard(boardId, currentUser.getId());
            return ResponseEntity.ok("Board deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{boardId}/invite")
    public ResponseEntity<?> inviteUserToBoard(@PathVariable Long boardId, @RequestParam Long userId) {
        try {
            boardService.inviteUserToBoard(boardId, userId);
            return ResponseEntity.ok("User invited successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{boardId}/collaborators")
    public ResponseEntity<Set<UserDTO>> getCollaborators(@PathVariable Long boardId) {
        try {
            Set<UserDTO> collaborators = boardService.getBoardCollaborators(boardId);
            return ResponseEntity.ok(collaborators);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
