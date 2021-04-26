package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.*;
import by.golik.dealerstat.exception.NotEnoughRightException;
import by.golik.dealerstat.service.CommentService;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.CommentDTO;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;
import by.golik.dealerstat.service.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    private final CommentService commentService;

    private final UserService userService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService, UserService userService,
                                CommentService commentService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
        this.commentService = commentService;
    }

    /**
     * Создается
     * @param gameObject
     * @return
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> post(@RequestBody GameObject gameObject) {
        HttpHeaders headers = new HttpHeaders();
        if (gameObject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.gameObjectService.createGameObject(gameObject);
        return new ResponseEntity<>(gameObject, headers, HttpStatus.CREATED);
    }

    /**
     * Все видны
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameObject>> getAll() {
        List<GameObject> gameObjects = this.gameObjectService.findAllGameObjects();
        return generateListResponse(gameObjects);
    }

    /**
     * Достаются по айди
     * @param id
     * @param principal
     * @return
     */
    @GetMapping("/{id}")
    public GameObjectDTO getGameObject(@PathVariable("id") long id, Principal principal) {
        GameObject gameObject = gameObjectService.findGameObjectById(id);

        if (gameObject.getStatus().equals(Status.SOLD) && principal == null) {
//            throw new NotEnoughRightException("You can't browse this post!");
        }
        return Mapper.convertToPostDTO(gameObject);
    }

    /**
     * Не подтвержденные можно подтвердить
     * @param id
     */
    @PostMapping("/{id}/approve")
    public void approveGameObject(@PathVariable("id") long id) {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        gameObjectService.approveGameObject(gameObject);
    }


    /**
     * Можно увидеть все неподтвржденные (подтвержденные тоже видны)
     * @param id
     * @return
     */
    @GetMapping("/{id}/unapproved")
    public GameObjectDTO getUnconfirmedGameObject(@PathVariable("id") long id) {
        return Mapper.convertToPostDTO(gameObjectService.getUnconfirmedGameObject(id));
    }
    
    @GetMapping("/my")
    public List<GameObjectDTO> getAllMyPosts(Principal principal) {
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        return Mapper.convertToListPostDTO(gameObjectService.getAllMyGameObjects(user));
    }

    /**
     * ссылка на список игр работает
     * @return
     */
    @GetMapping("/games")
    public List<GameDTO> getAllGames() {
        return Mapper.convertToListGameDTO(gameObjectService.getAllGames());
    }

    /**
     * Нет прав (юзер д.б. равен юзеру)
     * @param id
     * @param gameObjectDTO
     * @param principal
     */
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

    /**
     * Нужны права для удаления
     * @param id
     * @param principal
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") long id, Principal principal) {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!userService.isAdmin(user) && !gameObject.getAuthor().equals(user)) {
//              throw new NotEnoughRightException("You can't delete this post!");
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
