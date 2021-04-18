package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.Game;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface GameRepo {
    void addGame(Game game);

    void updateGame(Game game);

    void removeGame(Integer id);

    Game getGameById(Integer id);

    List<Game> listGames();
}
