package com.example.trello.service;

import com.example.trello.model.Board;
import com.example.trello.model.dbList; 
import com.example.trello.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private BoardService boardService;

    @Transactional
    public dbList createList(Long boardId, String listName, Long userId) {
        Board board = boardService.validateBoardOwnership(boardId, userId);
        dbList list = new dbList();
        list.setName(listName);
        list.setBoard(board);
        return listRepository.save(list);
    }

    @Transactional
    public void deleteList(Long listId, Long userId) {
        dbList list = listRepository.findById(listId)
            .orElseThrow(() -> new IllegalArgumentException("List not found"));
        if (!list.getBoard().getCreator().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized to delete this list");
        }
        listRepository.delete(list);
    }
}
