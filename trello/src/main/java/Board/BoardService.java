package Board;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/board")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardService {

    @PersistenceContext
    EntityManager em;

    // Method to create a new board
    @POST
    @Path("create")
    public String createBoard(BoardEntity board) {
        em.persist(board);
        return "Board created successfully";
    }

    // Method to delete an existing board
    @DELETE
    @Path("delete/{id}")
    public String deleteBoard(@PathParam("id") Long id) {
        BoardEntity board = em.find(BoardEntity.class, id);
        if (board != null) {
            em.remove(board);
            return "Board deleted successfully";
        } else {
            return "Board not found";
        }
    }

    // Method to retrieve all boards
    @GET
    @Path("all")
    public List<BoardEntity> getAllBoards() {
        return em.createQuery("SELECT b FROM BoardEntity b", BoardEntity.class)
                .getResultList();
    }

    // Add additional methods as needed for board management
}
