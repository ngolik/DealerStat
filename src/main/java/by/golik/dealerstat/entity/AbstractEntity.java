package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author Nikita Golik
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    protected Calendar createdAt;

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
