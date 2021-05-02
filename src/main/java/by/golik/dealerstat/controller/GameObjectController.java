package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.*;
import by.golik.dealerstat.exception.NotEnoughRightException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;
import by.golik.dealerstat.service.util.GameDtoAssembler;
import by.golik.dealerstat.service.util.GameObjectDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Nikita Golik
 */
@RestController
@RequestMapping("/objects")
public class GameObjectController {


    private final GameObjectService gameObjectService;

    private final UserService userService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService, UserService userService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
    }

    /**
     * Method for creation game object
     * @param gameObjectDTO - value object Dto of Game object
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException - if game object doesn't exist
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createGameObject(@RequestBody @Valid GameObjectDTO gameObjectDTO,
                                 Principal principal) throws ResourceNotFoundException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());
        List<Game> games = gameObjectService.getGamesByGameDTO(gameObjectDTO.getGames());
        GameObject gameObject = GameObjectDtoAssembler.toEntity(gameObjectDTO,
                userService.isAdmin(user), user, games);

        gameObjectService.createGameObject(gameObject);
    }

    /**
     * Method for confirming Game Object
     * @param id - unique identifier of Game object
     * @throws ResourceNotFoundException - if game object doesn't exist
     */
    @PostMapping("/{id}/approve")
    public void approveGameObject(@PathVariable("id") int id) throws ResourceNotFoundException {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);

        gameObjectService.approveGameObject(gameObject);
    }

    /**
     * Method for finding Game object By id. You can see it only after confirming
     * @param id - unique identifier of Game object
     * @param principal - the currently logged in user
     * @return - object value dto of Game Object
     * @throws NotEnoughRightException - if user doesn't have enough rights
     * @throws ResourceNotFoundException - if game object doesn't exist
     */
    @GetMapping("/{id}")
    public GameObjectDTO getGameObject(@PathVariable("id") int id, Principal principal) throws NotEnoughRightException, ResourceNotFoundException {
        GameObject gameObject = gameObjectService.getGameObjectById(id);

        if (gameObject.getStatus().equals(Status.SOLD) && principal == null) {
            throw new NotEnoughRightException("You can't browse this game object!");
        }
        return GameObjectDtoAssembler.toDto(gameObject);
    }

    /**
     * Method for finding unconfirmed Game Objects
     * @param id - unique identifier of Game object
     * @return - object value dto of Game Object
     * @throws ResourceNotFoundException - if game object doesn't exist
     */
    @GetMapping("/{id}/unapproved")
    public GameObjectDTO getUnapprovedGameObject(@PathVariable("id") int id) throws ResourceNotFoundException {
        return GameObjectDtoAssembler.toDto(gameObjectService.getUnconfirmedGameObject(id));
    }

    /**
     * Method to find all game objects
     * @param principal - the currently logged in user
     * @return - object value dto of list Game Objects
     */
    @GetMapping
    public List<GameObjectDTO> getAllObjects(Principal principal) {
        if (principal == null) {
            return GameObjectDtoAssembler.toDtoList(gameObjectService.getAllAvailableGameobjects());
        }
        else {
            return GameObjectDtoAssembler.toDtoList(gameObjectService.getAllGameobjects());
        }
    }

    /**
     * Method to find all game objects by it's owner
     * @param principal - the currently logged in user
     * @return - object value dto list of Game Objects
     * @throws ResourceNotFoundException - if game object doesn't exist
     */
    @GetMapping("/my")
    public List<GameObjectDTO> getAllMyGameObjects(Principal principal) throws ResourceNotFoundException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        return GameObjectDtoAssembler.toDtoList(gameObjectService.getAllMyGameObjects(user));
    }

    /**
     * Method for getting a list of all games
     * @return - object value dto list of Game Objects
     */
    @GetMapping("/games")
    public List<GameDTO> getAllGames() {
        return GameDtoAssembler.toDtoList(gameObjectService.getAllGames());
    }

    /**
     * Method to update Game Object By it's
     * @param id - unique identifier of Game object
     * @param gameObjectDTO - value object Dto of Game object
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException - if game object doesn't exist
     * @throws NotEnoughRightException - if user doesn't have enough rights
     */
    @PutMapping("/{id}")
    public void updateGameObject(@PathVariable("id") int id,
                                 @RequestBody @Valid GameObjectDTO gameObjectDTO,
                                 Principal principal) throws ResourceNotFoundException, NotEnoughRightException {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!gameObject.getAuthor().equals(user)) {
            throw new NotEnoughRightException("You can't change this game object!");
        }
        gameObjectService.updateGameObject(gameObject, gameObjectDTO, userService.isAdmin(user));
    }

    /**
     * Method to delete Game Object
     * @param id - unique identifier of Game object
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException - if game object doesn't exist
     * @throws NotEnoughRightException - if user doesn't have enough rights
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameObject(@PathVariable("id") int id, Principal principal) throws ResourceNotFoundException, NotEnoughRightException {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!userService.isAdmin(user) && !gameObject.getAuthor().equals(user)) {
            throw new NotEnoughRightException("You can't delete this game object!");
        }
        gameObjectService.deleteGameObject(gameObject);
    }
}
