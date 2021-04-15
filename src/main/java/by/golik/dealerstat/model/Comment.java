package by.golik.dealerstat.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author Nikita Golik
 */

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment extends AbstractEntity {
    @Column
    private String message;

    private String tag;

    public Comment() {
    }

    public Comment(String message, String tag) {
        this.message = message;
        this.tag = tag;
    }

    @Column
    private Calendar createdAt;

    @Column
    private int rate;

    @ManyToOne
    @JoinColumn(name="author_id")
    @JsonBackReference(value = "user-comment")
    private User author;

    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name="game_object_id")
    @JsonBackReference (value = "gameObject-comment")
    private GameObject gameObject;

}
