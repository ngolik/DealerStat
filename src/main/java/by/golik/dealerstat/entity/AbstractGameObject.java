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
 * Prototype of GameObject Entity
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
