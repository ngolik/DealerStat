package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.Status;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;
import by.golik.dealerstat.service.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGameObject(@RequestBody @Valid GameObjectDTO gameObjectDTO,
                           Principal principal) {
        User user = userService.getUserByEmailAndEnabled(principal.getName());
        List<Game> games = gameObjectService.getGamesByGameDTOS(gameObjectDTO.getGames());
        GameObject gameObject = Mapper.convertToGameObject(gameObjectDTO,
                userService.isAdmin(user), user, games);

        gameObjectService.createGameObject(gameObject);
    }

    @PostMapping("/{id}/approve")
    public void approveGameObject(@PathVariable("id") long id) {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);

        gameObjectService.approveGameObject(gameObject);
    }

    @GetMapping("/{id}")
    public GameObjectDTO getGameObject(@PathVariable("id") long id, Principal principal) {
        GameObject gameObject = gameObjectService.getGameObject(id);

        if (gameObject.getStatus().equals(Status.SOLD) && principal == null) {
//            throw new NotEnoughRightException("You can't browse this post!");
        }
        return Mapper.convertToPostDTO(gameObject);
    }

    @GetMapping("/{id}/unapproved")
    public GameObjectDTO getUnconfirmedGameObject(@PathVariable("id") long id) {
        return Mapper.convertToPostDTO(gameObjectService.getUnconfirmedGameObject(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameObject>> getAll() {
        List<GameObject> gameObjects = this.gameObjectService.findAllGameObjects();
        return generateListResponse(gameObjects);
    }

//    @GetMapping
//    public List<GameObjectDTO> getAllGameObjects(Principal principal) {
//        if (principal == null) {
//            //todo available
//            return Mapper.convertToListPostDTO(gameObjectService.getAllGameObjects());
//        }
//        else {
//            return Mapper.convertToListPostDTO(gameObjectService.getAllGameObjects());
//        }
//    }

    @GetMapping("/my")
    public List<GameObjectDTO> getAllMyPosts(Principal principal) {
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        return Mapper.convertToListPostDTO(gameObjectService.getAllMyGameObjects(user));
    }

    @GetMapping("/games")
    public List<GameDTO> getAllGames() {
        return Mapper.convertToListGameDTO(gameObjectService.getAllGames());
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable("id") long id,
                           @RequestBody @Valid GameObjectDTO gameObjectDTO,
                           Principal principal) {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!gameObject.getAuthor().equals(user)) {
//            throw new NotEnoughRightException("You can't change this post!");
        }
        gameObjectService.updateGameObject(gameObject, gameObjectDTO, userService.isAdmin(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") long id, Principal principal) {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!userService.isAdmin(user) && !gameObject.getAuthor().equals(user)) {
//            throw new NotEnoughRightException("You can't delete this post!");
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
