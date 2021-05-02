package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Game;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

/**
 * Interface with methods to action with {@link Game} Game Entity
 * @author Nikita Golik
 */
public interface GameService {

    /**
     * This method allows to find game by it's id
     * @param id - unique identifier
     * @return Entity model of Game
     */
    public Optional<Game> findByGameId(int id);

    /**
     * This method allows to find all games
     * @return - list of Entity model of Game
     */
    public List<Game> findAllGames();

    /**
     * This method allows to create Entity game
     * @param game - Entity model of Game
     */
    public void saveGame(Game game);

    /**
     * This method allows to delete game by it's id
     * @param id - unique identifier
     */
    public void deleteGameById(int id);

    /**
     * This method allows to find game by it's id
     * @param game - Entity model of Game
     * @param id - unique identifier
     * @return - status of response
     */
    HttpStatus update(Game game, int id);
}
