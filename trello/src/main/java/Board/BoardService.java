package Board;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import User.UserEntity;

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

    // Method to invite a user to collaborate on a board
    @POST
    @Path("invite/{boardId}")
    public String inviteUserToBoard(@PathParam("boardId") Long boardId, UserEntity user, @QueryParam("userId") Long loggedInUserId) {
        BoardEntity board = em.find(BoardEntity.class, boardId);
        if (board != null) {
            // Check if the logged-in user is the owner of the board (TeamLeader)
            if (loggedInUserId.equals(board.getOwner().getId())) {
                // Add the user to the board's list of collaborators
                board.getCollaborators().add(user);
                em.merge(board);
                return "User invited successfully";
            } else {
                return "Only the board owner (TeamLeader) can invite users to collaborate";
            }
        } else {
            return "Board not found";
        }
    }
    
    
    // Method to kick a collaborator from the board
    @DELETE
    @Path("kickCollaborator/{boardId}")
    public String kickCollaboratorFromBoard(
            @PathParam("boardId") Long boardId,
            @QueryParam("userId") Long loggedInUserId,
            @QueryParam("collaboratorId") Long collaboratorId
    ) {
        BoardEntity board = em.find(BoardEntity.class, boardId);
        if (board != null) {
            // Check if the logged-in user is the owner of the board (TeamLeader)
            if (loggedInUserId.equals(board.getOwner().getId())) {
                // Get the collaborator to be kicked
                UserEntity collaborator = em.find(UserEntity.class, collaboratorId);
                if (collaborator != null && board.getCollaborators().contains(collaborator)) {
                    // Remove the collaborator from the board's list of collaborators
                    board.getCollaborators().remove(collaborator);
                    em.merge(board);
                    return "Collaborator kicked successfully";
                } else {
                    return "Collaborator not found on the board";
                }
            } else {
                return "Only the board owner (TeamLeader) can kick collaborators";
            }
        } else {
            return "Board not found";
        }
    }
}
