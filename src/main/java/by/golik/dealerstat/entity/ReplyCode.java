package by.golik.dealerstat.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Random;

/**
 * @author Nikita Golik
 */
@EqualsAndHashCode
@ToString
@Getter
@Entity
@NoArgsConstructor
public class ReplyCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar expiryDate;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    public ReplyCode(User user) {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        int length;

        this.code = String.valueOf(Math.abs((int) (random.nextDouble() * 10000)));
        length = code.length();
        for (int i = 0; i < (4 - length); i++) {
            code = "0" + code;
        }
        calendar.add(Calendar.MINUTE,15);
        this.expiryDate = calendar;
        this.user = user;
    }
}
