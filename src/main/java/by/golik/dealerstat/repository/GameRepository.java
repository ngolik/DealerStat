package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Nikita Golik
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByName(String name);

}
