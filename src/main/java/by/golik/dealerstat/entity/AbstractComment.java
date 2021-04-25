package by.golik.dealerstat.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author Nikita Golik
 */
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
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
