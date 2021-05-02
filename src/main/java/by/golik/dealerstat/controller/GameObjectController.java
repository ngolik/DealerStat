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

    @PostMapping("/{id}/approve")
    public void approveGameobject(@PathVariable("id") int id) throws ResourceNotFoundException {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);

        gameObjectService.approveGameObject(gameObject);
    }

    /**
     * Видны только после approve
     * @param id
     * @param principal
     * @return
     * @throws NotEnoughRightException
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public GameObjectDTO getGameObject(@PathVariable("id") int id, Principal principal) throws NotEnoughRightException, ResourceNotFoundException {
        GameObject gameObject = gameObjectService.getGameObjectById(id);

        if (gameObject.getStatus().equals(Status.SOLD) && principal == null) {
            throw new NotEnoughRightException("You can't browse this post!");
        }
        return GameObjectDtoAssembler.toDto(gameObject);
    }

    @GetMapping("/{id}/unapproved")
    public GameObjectDTO getUnapprovedGameobject(@PathVariable("id") int id) throws ResourceNotFoundException {
        return GameObjectDtoAssembler.toDto(gameObjectService.getUnconfirmedGameObject(id));
    }

    @GetMapping
    public List<GameObjectDTO> getAllObjects(Principal principal) {
        if (principal == null) {
            return GameObjectDtoAssembler.toDtoList(gameObjectService.getAllAvailableGameobjects());
        }
        else {
            return GameObjectDtoAssembler.toDtoList(gameObjectService.getAllGameobjects());
        }
    }

    @GetMapping("/my")
    public List<GameObjectDTO> getAllMyGameobjects(Principal principal) throws ResourceNotFoundException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        return GameObjectDtoAssembler.toDtoList(gameObjectService.getAllMyGameObjects(user));
    }

    @GetMapping("/games")
    public List<GameDTO> getAllGames() {
        return GameDtoAssembler.toDtoList(gameObjectService.getAllGames());
    }

    @PutMapping("/{id}")
    public void updateGameobject(@PathVariable("id") int id,
                                 @RequestBody @Valid GameObjectDTO gameObjectDTO,
                                 Principal principal) throws ResourceNotFoundException, NotEnoughRightException {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!gameObject.getAuthor().equals(user)) {
            throw new NotEnoughRightException("You can't change this post!");
        }
        gameObjectService.updateGameObject(gameObject, gameObjectDTO, userService.isAdmin(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameobject(@PathVariable("id") int id, Principal principal) throws ResourceNotFoundException, NotEnoughRightException {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!userService.isAdmin(user) && !gameObject.getAuthor().equals(user)) {
            throw new NotEnoughRightException("You can't delete this post!");
        }
        gameObjectService.deleteGameObject(gameObject);
    }
    protected ResponseEntity<List<GameObject>> generateListResponse(List<GameObject> gameObjects) {
        if (gameObjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gameObjects, HttpStatus.OK);
    }
}
