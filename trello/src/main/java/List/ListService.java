package List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/list")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListService {

    @PersistenceContext
    EntityManager em;

    // Method to create a new list
    @POST
    @Path("create")
    public String createList(ListEntity list) {
        em.persist(list);
        return "List created successfully";
    }

    // Method to delete an existing list
    @DELETE
    @Path("delete/{id}")
    public String deleteList(@PathParam("id") Long id) {
        ListEntity list = em.find(ListEntity.class, id);
        if (list != null) {
            em.remove(list);
            return "List deleted successfully";
        } else {
            return "List not found";
        }
    }

    // Method to retrieve all lists
    @GET
    @Path("all")
    public List<ListEntity> getAllLists() {
        return em.createQuery("SELECT l FROM ListEntity l", ListEntity.class)
                .getResultList();
    }

    // Add additional methods as needed for list management
}
