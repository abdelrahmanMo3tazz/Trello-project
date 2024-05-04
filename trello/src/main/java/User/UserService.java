package User;

import User.UserEntity.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/user")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

    @PersistenceContext
    private EntityManager em;

    // Method to register a new user
    @POST
    @Path("register")
    public void registerUser(String email, String userName, String password, Role role) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setUsername(userName);
        user.setRole(role);
        user.setPassword(password);
        em.persist(user);
    }

    // Method to log in a user
    @POST
    @Path("login")
    public UserEntity loginUser(String email, String password) {
        try {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email AND u.password = :password", UserEntity.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
        } catch (Exception e) {
            return null; // User not found
        }
    }

    // Method to update user profile
    @POST
    @Path("update")
    public void updateUserProfile(Long userId, String email, String password) {
        UserEntity user = em.find(UserEntity.class, userId);
        if (user != null) {
            user.setEmail(email);
            user.setPassword(password);
            em.merge(user);
        }
    }
}
