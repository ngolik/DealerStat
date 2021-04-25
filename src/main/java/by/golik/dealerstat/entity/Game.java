package by.golik.dealerstat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private long id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @ToString.Exclude
    private List<GameObject> gameobjects;

    @ManyToMany
    @ToString.Exclude
    private List<UnconfirmedGameObject> unconfirmedGameObjects;

    public Game(String name) {
        this.name = name;
    }
}
