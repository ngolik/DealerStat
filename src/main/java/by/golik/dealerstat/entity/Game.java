package by.golik.dealerstat.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * Class represents Game entity
 * @author Nikita Golik
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(unique = true)
    @NaturalId(mutable=true)
    private String name;

    @ManyToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GameObject> gameobjects;

    @ManyToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UnconfirmedGameObject> unconfirmedGameObjects;

    public Game(String name) {
        this.name = name;
    }
}
