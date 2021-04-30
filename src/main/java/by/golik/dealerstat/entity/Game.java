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
    protected Long id;

    @Column(unique = true)
    @NaturalId(mutable=true)
    private String name;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    private List<GameObject> gameobjects;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude

    private List<UnconfirmedGameObject> unconfirmedGameObjects;

    public Game(String name) {
        this.name = name;
    }
}
