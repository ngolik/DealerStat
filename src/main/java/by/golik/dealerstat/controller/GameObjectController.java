package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nikita Golik
 */
@RestController
@RequestMapping("/objects")
public class GameObjectController {

    private final GameObjectService gameObjectService;

    @Autowired
    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> get(@PathVariable Long id) {
        if (!this.gameObjectService.findByGameObjectId(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GameObject gameObject = this.gameObjectService.findByGameObjectId(id).get();
        return new ResponseEntity<>(gameObject, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameObject>> getAll() {
        List<GameObject> gameObjects = this.gameObjectService.findAllGameObjects();
        return generateListResponse(gameObjects);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> post(@RequestBody GameObject gameObject) {
        HttpHeaders headers = new HttpHeaders();
        if (gameObject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.gameObjectService.saveGameObject(gameObject);
        return new ResponseEntity<>(gameObject, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> put(@RequestBody GameObject gameObject, @PathVariable Long id) {
        return new ResponseEntity<>(gameObjectService.update(gameObject, id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameObject> delete(@PathVariable Long id) {
        gameObjectService.deleteGameObjectById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected ResponseEntity<List<GameObject>> generateListResponse(List<GameObject> gameObjects) {
        if (gameObjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gameObjects, HttpStatus.OK);
    }

    @GetMapping("/my")
    public void getUsersObjects() {};
}
