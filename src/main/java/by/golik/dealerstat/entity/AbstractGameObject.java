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
public abstract class AbstractGameObject extends AbstractEntity {

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false)
    protected String text;

    @Column(nullable = false)
    protected Status status;

    public AbstractGameObject(String title, String text, Status status) {
        this.title = title;
        this.text = text;
        this.status = status;
    }
}
