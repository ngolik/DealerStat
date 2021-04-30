package by.golik.dealerstat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * Class represents Entity Token
 * @author Nikita Golik
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar expiryDate;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    public Token(User user) {
        Calendar calendar = Calendar.getInstance();

        this.user = user;
        this.token = UUID.randomUUID().toString();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        this.expiryDate = calendar;
    }
}
