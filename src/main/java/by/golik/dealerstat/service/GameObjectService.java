package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;

import java.util.List;

/**
 * Interface with methods to action with {@link GameObject} Game Object Entity
 * @author Nikita Golik
 */
public interface GameObjectService {

    /**
     * This method allows to find all game objects with {@link by.golik.dealerstat.entity.Status} 'AVAILABLE'
     * @return - list of Game Objects
     */
    List<GameObject> getAllAvailableGameobjects();

    /**
     * This method allows to find Game Object by it's Id
     * @param id - unique identifier of game object
     * @return - entity model Game Object
     * @throws ResourceNotFoundException - if Game object Doesn't exist
     */
    GameObject getGameObjectById(int id) throws ResourceNotFoundException;

    /**
     * This method allows to create game object
     * @param gameObject - entity model of Game object
     */
    void createGameObject(GameObject gameObject);

    /**
     * This method allows to user with admin root approve Game Object from Trader
     * @param gameObject - entity model Game Object
     */
    void approveGameObject(GameObject gameObject);

    /**
     * This method allows to find Game Object, which didn't confirm by it's Id
     * @param id - unique identifier of game object
     * @return - entity model of Unconfirmed Game Object
     */
    GameObject getUnconfirmedGameObject(int id) throws ResourceNotFoundException;

    /**
     * This method allows to find all Owner's Game Objects
     * @param user - Entity model of Ownew
     * @return - list of all owner's game objects
     */
    List<GameObject> getAllMyGameObjects(User user);

    /**
     * This method allows to find all Games
     * @return - list of all games
     */
    List<Game> getAllGames();

    /**
     * This method allows to find all Game Objects
     * @return - list of all game objects
     */
    List<GameObject> getAllGameobjects();

    /**
     * This method allows to find all games by list of game dto models
     * @param gameDTO - object value of Game
     * @return - List of Games
     */
    List<Game> getGamesByGameDTO(List<GameDTO> gameDTO);

    /**
     * This method allows to update game object's information
     * @param gameObject - entity model GameObject
     * @param gameObjectDTO - calue object of Game Object DTO
     * @param admin - check if user has admin root
     */
    void updateGameObject(GameObject gameObject, GameObjectDTO gameObjectDTO, boolean admin);

    /**
     * This method allows to delete Game object by it's entity model
     * @param gameObject - Game Object Model
     */
    void deleteGameObject(GameObject gameObject);

    /**
     * This method allows to find Id of Game using it's name
     * @param names - names of games
     * @return - list of Id games
     */
    List<Integer> getGameIdByName(String[] names);
}
