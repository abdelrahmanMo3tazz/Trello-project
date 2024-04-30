package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//Annotating the class with @Entity indicates that it is a JPA entity.
//@Table(name = "lists") specifies the name of the database table associated with this entity.
//The id field is annotated with @Id to indicate it's the primary key.
//@GeneratedValue(strategy = GenerationType.IDENTITY) specifies that the ID should be generated automatically by the database.
//@Column(nullable = false) ensures that the name attribute cannot be null.
//@ManyToOne establishes a many-to-one relationship with the Board entity.
//@JoinColumn(name = "board_id", nullable = false) specifies the foreign key column name and ensures it cannot be null.
@Entity
@Table(name = "lists")
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    // Other fields and relationships as needed

    // Getters and setters
}
