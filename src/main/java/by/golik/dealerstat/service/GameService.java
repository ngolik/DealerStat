package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.Game;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface GameService {

    void addGame(Game game);

    void updateGame(Game game);

    void removeGame(Integer id);

    Game getGameById(Integer id);

    List<Game> listGames();
}
