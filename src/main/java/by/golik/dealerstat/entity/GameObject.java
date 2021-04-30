package by.golik.dealerstat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Class represents GameObject Entity
 * @author Nikita Golik
 */
@Entity
@Getter
@NoArgsConstructor
public class GameObject extends AbstractGameObject {


    @Setter
    @Column(nullable = false)
    private boolean approved;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn()
    private User author;

    @Setter
    @OneToOne(mappedBy = "gameobject", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private UnconfirmedGameObject unconfirmedGameObject;

    @JsonIgnore
    @OneToMany(mappedBy = "gameobject", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> comments;

    @JsonIgnore
    @Setter
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            joinColumns = @JoinColumn(name = "gameobjects_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

    @Builder
    public GameObject(String title, String text, Status status, boolean approved,
                User user, List<Game> games) {
        super(title, text, status);
        this.approved = approved;
        this.author = user;
        this.games = games;
    }
}
