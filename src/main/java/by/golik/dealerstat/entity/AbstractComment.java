package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Prototype of Comment Entity
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
