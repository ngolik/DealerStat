package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface GameObjectService {

    void createGameObject(GameObject gameObject);
    void approveGameObject(GameObject gameObject);
    public GameObject findGameObjectById(long id);
    public Optional<GameObject> findByGameObjectId(Long id);
    GameObject getUnconfirmedGameObject(long id);
    List<GameObject> getAllMyGameObjects(User user);
    List<GameObject> findAllGameObjects();

    List<Game> getAllGames();
    List<Game> getGamesByGameDTOS(List<GameDTO> gameDTOS);
    List<Long> getGameIdByName(String[] names);
    void updateGameObject(GameObject gameObject, GameObjectDTO gameObjectDTO, boolean admin);
    void deleteGameObject(GameObject gameObject);
    public void deleteGameById(Long id);
}
