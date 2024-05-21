package com.example.trello.service; 

import com.example.trello.dto.UserDTO;
import com.example.trello.model.Board;
import com.example.trello.model.Role;
import com.example.trello.model.User;
import com.example.trello.repository.BoardRepository;
import com.example.trello.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Set<UserDTO> getBoardCollaborators(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        return board.getCollaborators().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .collect(Collectors.toSet());
    }

    public Board findById(Long id) throws Exception {
        return boardRepository.findById(id)
                .orElseThrow(() -> new Exception("Board not found with id: " + id));
    }

    // Create a new board
    public Board createBoard(Board board, Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (!user.getRole().equals(Role.TEAM_LEADER)) {
            throw new IllegalArgumentException("Only team leaders can create boards.");
        }

        // Check for unique board name
        if (boardRepository.existsByName(board.getName())) {
            throw new IllegalArgumentException("A board with the same name already exists.");
        }

        board.setCreator(user);
        return boardRepository.save(board);
    }

    // Delete a board
    public void deleteBoard(Long boardId, Long userId) throws Exception {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new Exception("Board not found with id: " + boardId));

        if (!board.getCreator().getId().equals(userId)) {
            throw new Exception("Only the creator can delete the board.");
        }

        boardRepository.delete(board);
    }

    // List all boards by a user
    public List<Board> findAllBoardsByUser(Long userId) {
        return boardRepository.findAllByCreatorId(userId);
    }

    public Board findBoardById(Long id) throws Exception {
        return boardRepository.findById(id)
                .orElseThrow(() -> new Exception("Board not found with id: " + id));
    }

    /**
     * Invites a user to collaborate on a specific board.
     * 
     * @param boardId the ID of the board to which the user is being invited
     * @param userId  the ID of the user being invited to the board
     * @throws Exception if the board or user does not exist or the invitation fails
     */
    @Transactional
    public void inviteUserToBoard(Long boardId, Long userId) throws Exception {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new Exception("Board not found with id: " + boardId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));

        // Check if the user is already a collaborator
        if (board.getCollaborators().contains(user)) {
            throw new Exception("User already a collaborator on this board.");
        }

        board.getCollaborators().add(user);
        boardRepository.save(board);
    }

     /**
     * Validates if the specified user is the creator of the board.
     *
     * @param boardId The ID of the board to check.
     * @param userId The ID of the user to validate.
     * @return The board if the user is the creator.
     * @throws RuntimeException if the board does not exist or the user is not the creator.
     */
    public Board validateBoardOwnership(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> 
            new RuntimeException("Board not found with id: " + boardId));

        if (!board.getCreator().getId().equals(userId)) {
            throw new RuntimeException("User " + userId + " is not authorized to modify this board.");
        }

        return board;
    }

}
