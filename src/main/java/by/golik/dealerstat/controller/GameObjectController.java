package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.*;
import by.golik.dealerstat.exception.NotEnoughRightException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
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

    private final UserService userService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService, UserService userService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
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
     * @param
     * @return
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> get(@PathVariable Long id) throws NotEnoughRightException {
        if (!this.gameObjectService.findGameObjectById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GameObject gameObject = this.gameObjectService.findGameObjectById(id).get();
        if (gameObject.getStatus().equals(Status.SOLD)) {
            throw new NotEnoughRightException("You can't browse this gameobject!");
        }
        return new ResponseEntity<>(gameObject, HttpStatus.OK);
    }

    /**
     * Создается
     * @param gameObject
     * @return
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> createGameObject(@RequestBody GameObject gameObject) {
        HttpHeaders headers = new HttpHeaders();
        if (gameObject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.gameObjectService.createGameObject(gameObject);
        return new ResponseEntity<>(gameObject, headers, HttpStatus.CREATED);
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
    public ResponseEntity<GameObject> getUnconfirmedGameObject(@PathVariable("id") long id) {
        if (!this.gameObjectService.findGameObjectById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        return new ResponseEntity<>(gameObject, HttpStatus.OK);
    }

    /**
     *
     * @param principal
     * @return
     */
    @GetMapping("/my")
    public ResponseEntity<List<GameObject>> getAllMyGameObjects(Principal principal) throws ResourceNotFoundException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());
        List<GameObject> gameObjects = gameObjectService.getAllMyGameObjects(user);
        return generateListResponse(gameObjects);
    }

    /**
     * ссылка на список игр работает
     * @return
     */
    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameObjectService.getAllGames();
    }

    /**
     * todo Обновление проходит(нужна проверка на юзера)
     * @param id
     * @param
     */
    @PutMapping("/{id}")
    public ResponseEntity<GameObject> updateGameObject(@RequestBody GameObject gameObject, @PathVariable Long id) {
        return new ResponseEntity<>(gameObjectService.update(gameObject, id));
    }

    /**
     * todo Удаление проходит(нужна проверка на юзера)
     * @param id
     * @param
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> deleteGameObject(@PathVariable("id") long id) {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);
        gameObjectService.deleteGameObject(gameObject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected ResponseEntity<List<GameObject>> generateListResponse(List<GameObject> gameObjects) {
        if (gameObjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gameObjects, HttpStatus.OK);
    }
}
