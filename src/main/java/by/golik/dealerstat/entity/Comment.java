package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Nikita Golik
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends AbstractComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Setter
    @Column(nullable = false)
    private boolean approved;

    @ManyToOne
    @JoinColumn(nullable = false)
    private GameObject gameobject;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private UnconfirmedComment unconfirmedComment;

    public Comment(String message, int rate, boolean approved,
                   GameObject gameobject, User user) {
        super(message, rate);
        this.approved = approved;
        this.gameobject = gameobject;
        this.author = user;
    }

}
