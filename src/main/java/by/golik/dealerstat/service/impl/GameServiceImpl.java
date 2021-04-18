package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.repository.GameRepo;
import by.golik.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nikita Golik
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private GameRepo gameRepo;

    @Autowired
    public void setGameRepo(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    @Override
    public void addGame(Game game) {
        this.gameRepo.addGame(game);
    }

    @Override
    public void updateGame(Game game) {
        this.gameRepo.updateGame(game);
    }

    @Override
    public void removeGame(Integer id) {
        this.gameRepo.removeGame(id);
    }

    @Override
    public Game getGameById(Integer id) {
        return this.gameRepo.getGameById(id);
    }

    @Override
    public List<Game> listGames() {
        return this.gameRepo.listGames();
    }
}
