package by.golik.dealerstat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikita Golik
 */
@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize
public class Game  extends AbstractEntity {

    @Column(unique = true)
    private String name;

    @OneToMany (mappedBy = "game")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference(value = "game-gameObject")
    private List<GameObject> gameObjects;
}
