package by.golik.dealerstat.service;


import by.golik.dealerstat.entity.Game;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface GameService {
    public Optional<Game> findByGameId(Long id);
    public List<Game> findAllGames();
    public void saveGame(Game game);
    public void deleteGameById(Long id);
}
