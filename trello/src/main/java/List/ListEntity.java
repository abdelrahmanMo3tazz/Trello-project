package List;

import Board.BoardEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "lists")
public class ListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private BoardEntity board;

    // Getters and setters
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

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }
}
