package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikita Golik
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class UnconfirmedGameObject extends AbstractGameObject {

    @OneToOne
    @JoinColumn(nullable = false)
    private GameObject gameobject;

    @Setter
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "unconfirmed_gameobject_hasid"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

    public UnconfirmedGameObject(String title, String text, Status status,
                          List<Game> games, GameObject gameobject) {
        super(title, text, status);
        this.gameobject = gameobject;
        this.games = games;
    }
}
