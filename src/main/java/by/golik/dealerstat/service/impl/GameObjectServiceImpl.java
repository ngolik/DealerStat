package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.*;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.repository.GameObjectRepository;
import by.golik.dealerstat.repository.GameRepository;
import by.golik.dealerstat.repository.UnconfirmedGameObjectRepository;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;
import by.golik.dealerstat.service.util.UnconfirmedGameObjectDtoAssembler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Nikita Golik
 */
@Service
@Transactional
@Log4j2
public class GameObjectServiceImpl implements GameObjectService {

    private final GameObjectRepository gameObjectRepository;
    private final UnconfirmedGameObjectRepository unconfirmedGameObjectRepository;
    private final GameRepository gameRepository;

    @Autowired
    public GameObjectServiceImpl(GameObjectRepository gameObjectRepository, UnconfirmedGameObjectRepository unconfirmedGameObjectRepository, GameRepository gameRepository) {
        this.gameObjectRepository = gameObjectRepository;
        this.unconfirmedGameObjectRepository = unconfirmedGameObjectRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void createGameObject(GameObject gameObject) {
        gameObjectRepository.save(gameObject);
        log.info("GameObject " + gameObject + " has been created.");
    }

    @Override
    public void approveGameObject(GameObject gameObject) {
        gameObject.setApproved(true);
        if (gameObject.getUnconfirmedGameObject() != null) {
            unconfirmedGameObjectRepository.delete(gameObject.getUnconfirmedGameObject());
        }
        gameObjectRepository.save(gameObject);
        log.info("GameObject " + gameObject + " has been successfully approved.");
    }

    @Override
    @Transactional(readOnly = true)
    public GameObject getGameObjectById(int id) throws ResourceNotFoundException {
        Optional<GameObject> optionalGameObject = gameObjectRepository.findByIdAndApprovedTrue(id);

        if (!optionalGameObject.isPresent()) {
            log.info("Post with " + id + " doesn't exist!");
            throw new ResourceNotFoundException("This post doesn't exist!");
        }
        return optionalGameObject.get();
    }

    @Override
    @Transactional(readOnly = true)
    public GameObject getUnconfirmedGameObject(int id) {
        Optional<GameObject> optionalPost = gameObjectRepository.findById(id);
        GameObject gameObject;

        if (!optionalPost.isPresent()) {
            log.info("GameObject with " + id + " doesn't exist!");
//            throw new ResourceNotFoundException("This post doesn't exist!");
        }
        gameObject = optionalPost.get();
        if (gameObject.getUnconfirmedGameObject() != null) {
            UnconfirmedGameObject unconfirmedGameObject = gameObject.getUnconfirmedGameObject();
            List<Game> games;

            if (unconfirmedGameObject.getGames() != null) {
                games = new ArrayList<>(unconfirmedGameObject.getGames());
            } else {
                games = null;
            }
            gameObject.setTitle(unconfirmedGameObject.getTitle());
            gameObject.setText(unconfirmedGameObject.getText());
            gameObject.setStatus(unconfirmedGameObject.getStatus());
            gameObject.setGames(games);
        }
        return gameObject;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameObject> getAllMyGameObjects(User user) {
        return gameObjectRepository.findAllByAuthor(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameObject> findAllGameObjects() {
        return gameObjectRepository.findAll();
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<GameObject> getAllAvailableGameObjects() {
//        return gameObjectRepository.findAllByStatusIsAvailable();
//    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> getGamesByGameDTOS(List<GameDTO> gameDTOS) {
        List<Game> games = new ArrayList<>();

        if (gameDTOS == null) return games;
        gameDTOS = gameDTOS.stream().distinct().collect(Collectors.toList());
        for (GameDTO gameDTO : gameDTOS) {
            Optional<Game> optionalGame = gameRepository.findByName(gameDTO.getName());
            Game game;

            if (optionalGame.isPresent()) {
                game = optionalGame.get();
            } else {
                game = new Game(gameDTO.getName());
                gameRepository.save(game);
                log.info("Game " + game + " has been created.");
            }
            games.add(game);
        }
        return games;
    }

    @Override
    public void updateGameObject(GameObject gameObject, GameObjectDTO gameObjectDTO, boolean admin) {
        List<Game> games = getGamesByGameDTOS(gameObjectDTO.getGames());

        if (admin || !gameObject.isApproved()) {
            gameObject.setTitle(gameObjectDTO.getTitle());
            gameObject.setText(gameObjectDTO.getText());
            gameObject.setStatus(Status.valueOf(gameObjectDTO.getStatus()));
            gameObject.setGames(games);
        } else {
            UnconfirmedGameObject unconfirmedGameObject = UnconfirmedGameObjectDtoAssembler
                    .convertToUnconfirmedGameObject(gameObjectDTO, games, gameObject);

            unconfirmedGameObjectRepository.deleteByGameobject(gameObject);
            unconfirmedGameObjectRepository.save(unconfirmedGameObject);
        }
        gameObjectRepository.save(gameObject);
        log.info("GameObject " + gameObject + " has been updated.");
    }

    @Override
    public HttpStatus update(GameObject gameObject, int id) {
        Optional<GameObject> gameObjectOptional = gameObjectRepository.findById(id);
        if (!gameObjectOptional.isPresent()) {
            return HttpStatus.NOT_FOUND;
        }
        gameObject.setId(id);
        gameObjectRepository.save(gameObject);
        return HttpStatus.OK;
    }

    @Override
    public void deleteGameObject(GameObject gameObject) {
        gameObjectRepository.delete(gameObject);
        log.info("GameObject " + gameObject + " has been deleted.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getGameIdByName(String[] names) {
        List<Integer> idList = new ArrayList<>();
        boolean nonExist = false;

        for (String name: names) {
            Optional<Game> optionalGame = gameRepository.findByName(name);

            if (optionalGame.isPresent() && optionalGame.get().getGameobjects() != null) {
                idList.add(optionalGame.get().getId());
            } else {
                nonExist = true;
            }
        }
        if (idList.isEmpty() && nonExist) {
            return null;
        } else {
            return idList;
        }
    }

    @Transactional
    @Override
    public List<GameObject> getAllAvailableGameobjects() {
        return gameObjectRepository.findAllByStatusIsAvailable();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GameObject> getAllGameobjects() {
        return gameObjectRepository.findAllByApprovedIsTrue();
    }

}
