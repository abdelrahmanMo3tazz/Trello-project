package service;

import model.Board;
import java.util.List;

public interface BoardService {
    Board createBoard(Board board);
    List<Board> getAllBoards();
    // Add other methods as needed
}
