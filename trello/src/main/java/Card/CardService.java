package Card;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/card")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardService {

    @PersistenceContext
    EntityManager em;

    // Method to create a new card
    @POST
    @Path("create")
    public String createCard(CardEntity card) {
        em.persist(card);
        return "Card created successfully";
    }

    // Method to delete an existing card
    @DELETE
    @Path("delete/{id}")
    public String deleteCard(@PathParam("id") Long id) {
        CardEntity card = em.find(CardEntity.class, id);
        if (card != null) {
            em.remove(card);
            return "Card deleted successfully";
        } else {
            return "Card not found";
        }
    }

    // Method to retrieve all cards
    @GET
    @Path("all")
    public List<CardEntity> getAllCards() {
        return em.createQuery("SELECT c FROM CardEntity c", CardEntity.class)
                .getResultList();
    }

    // Add additional methods as needed for card management
}
