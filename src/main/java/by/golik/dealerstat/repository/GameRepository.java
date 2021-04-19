package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Nikita Golik
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

}
