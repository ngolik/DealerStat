package by.golik.dealerstat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author Nikita Golik
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment extends AbstractEntity {

    @Column
    private String message;

    @Column
    private Calendar createdAt;

    @Column
    private int rate;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference(value = "user-comment")
    private User author;

    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name="gameObject_id")
    @JsonBackReference (value = "gameObject-comment")
    private GameObject gameObject;

}
