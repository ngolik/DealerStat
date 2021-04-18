package by.golik.dealerstat.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Nikita Golik
 */

@Entity
@Table(name = "comment")
public class Comment {

    public Comment() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "rate")
    private int rate;

    @Column(nullable = false)
    private boolean approved;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Comment(String message, int rate, boolean approved, Date createdAt) {
        this.message = message;
        this.rate = rate;
        this.approved = approved;
        this.createdAt = createdAt;
    }
}
