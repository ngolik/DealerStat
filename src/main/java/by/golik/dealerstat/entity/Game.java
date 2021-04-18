package by.golik.dealerstat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Nikita Golik
 */
@Entity
@Table(name = "game")
public class Game extends AbstractEntity {
    public Game() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
