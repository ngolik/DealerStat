package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Nikita Golik
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class UnconfirmedComment extends AbstractComment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Comment comment;

    public UnconfirmedComment(String message, int rate, Comment comment) {
        super(message, rate);
        this.comment = comment;
    }
}
