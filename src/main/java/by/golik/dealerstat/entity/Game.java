package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikita Golik
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
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
