package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents entity Unconfirmed Comment
 * @author Nikita Golik
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class UnconfirmedComment extends AbstractComment{

    @OneToOne
    @JoinColumn(nullable = false)
    private Comment comment;

    public UnconfirmedComment(String message, int rate, Comment comment) {
        super(message, rate);
        this.comment = comment;
    }
}
