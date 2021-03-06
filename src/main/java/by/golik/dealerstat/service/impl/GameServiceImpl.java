package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.repository.GameRepository;
import by.golik.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
@Service

@Transactional
public class GameServiceImpl implements GameService {

    GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Optional<Game> findByGameId(int id) {
        return gameRepository.findById(id);
    }

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void deleteGameById(int id) {
        gameRepository.deleteById(id);
    }

    @Override
    public HttpStatus update(Game game, int id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (!gameOptional.isPresent()) {
            return HttpStatus.NOT_FOUND;
        }
        game.setId(id);
        gameRepository.save(game);
        return HttpStatus.OK;
    }
}
