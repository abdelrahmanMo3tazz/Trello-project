package User;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    // Method to register a new user
    public void registerUser(String email, String password) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(password);
        em.persist(user);
    }

    // Method to log in a user
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
    public void updateUserProfile(Long userId, String email, String password) {
        UserEntity user = em.find(UserEntity.class, userId);
        if (user != null) {
            user.setEmail(email);
            user.setPassword(password);
            em.merge(user);
        }
    }
}
