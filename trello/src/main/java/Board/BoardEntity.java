package Board;
import java.util.HashSet;

import java.util.Set;

import User.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "boards")
public class BoardEntity {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String name;

	    @ManyToOne
	    @JoinColumn(name = "owner_id", nullable = false)
	    private UserEntity owner;

	    @ManyToMany
	    @JoinTable(
	        name = "board_collaborators",
	        joinColumns = @JoinColumn(name = "board_id"),
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	    private Set<UserEntity> collaborators = new HashSet<>();

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
    
    public void addCollaborator(UserEntity collaborator) {
        collaborators.add(collaborator);
    }

    public void removeCollaborator(UserEntity collaborator) {
        collaborators.remove(collaborator);
    }

    public Set<UserEntity> getCollaborators() {
        return collaborators;
    }
    public UserEntity getOwner() {
        return owner;
    }
}
