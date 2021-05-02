package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Games
 * @author Nikita Golik
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByName(String name);

}
