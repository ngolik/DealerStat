package by.golik.dealerstat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Nikita Golik
 */
@Entity
@Table(name = "game")
@Getter
@Setter
public class Game extends AbstractEntity {
    public Game() {
    }

    @Column(unique = true)
    private String name;

}
