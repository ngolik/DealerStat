package by.golik.dealerstat.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

/**
 * @author Nikita Golik
 */
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractComment extends AbstractEntity {

    @Setter
    private String message;

    @Column(nullable = false)
    private int rate;

    public AbstractComment(String message, int rate) {
        this.message = message;
        this.rate = rate;
    }
}
