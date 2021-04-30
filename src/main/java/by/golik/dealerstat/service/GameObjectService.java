package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface GameObjectService {

    List<GameObject> getAllAvailableGameobjects();
    GameObject getGameObjectById(int id) throws ResourceNotFoundException;
    void createGameObject(GameObject gameObject);
    void approveGameObject(GameObject gameObject);
    GameObject getUnconfirmedGameObject(int id);
    List<GameObject> getAllMyGameObjects(User user);
    List<GameObject> findAllGameObjects();
    List<Game> getAllGames();
    List<GameObject> getAllGameobjects();
    List<Game> getGamesByGameDTOS(List<GameDTO> gameDTOS);
    HttpStatus update(GameObject gameObject, int id);
    void updateGameObject(GameObject gameObject, GameObjectDTO gameObjectDTO, boolean admin);
    void deleteGameObject(GameObject gameObject);
    List<Integer> getGameIdByName(String[] names);
}
