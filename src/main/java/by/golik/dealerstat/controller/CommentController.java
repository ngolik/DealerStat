package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.Status;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.NotEnoughRightException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.CommentService;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;
import by.golik.dealerstat.service.util.GameDtoAssembler;
import by.golik.dealerstat.service.util.GameObjectDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Nikita Golik
 */

@RestController
@RequestMapping("/")
public class CommentController {

      private final CommentService commentService;

      private final GameObjectService gameObjectService;

      private final UserService userService;

    @Autowired
    public CommentController(GameObjectService gameObjectService, UserService userService,
                             CommentService commentService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGameObject(@RequestBody @Valid GameObjectDTO gameObjectDTO,
                           Principal principal) throws ResourceNotFoundException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());
        List<Game> games = gameObjectService.getGamesByGameDTOS(gameObjectDTO.getGames());
        GameObject gameObject = GameObjectDtoAssembler.convertToGameObject(gameObjectDTO,
                userService.isAdmin(user), user, games);

        gameObjectService.createGameObject(gameObject);
    }

    @PostMapping("/{id}/approve")
    public void approveGameobject(@PathVariable("id") int id) {
        GameObject gameObject = gameObjectService.getUnconfirmedGameObject(id);

        gameObjectService.approveGameObject(gameObject);
    }

    @GetMapping("/{id}")
    public GameObjectDTO getGameObject(@PathVariable("id") int id, Principal principal) throws NotEnoughRightException, ResourceNotFoundException {
        GameObject gameObject = gameObjectService.getGameObjectById(id);

        if (gameObject.getStatus().equals(Status.SOLD) && principal == null) {
            throw new NotEnoughRightException("You can't browse this post!");
        }
        return GameObjectDtoAssembler.convertToGameObjectDTO(gameObject);
    }

    @GetMapping("/{id}/unapproved")
    public GameObjectDTO getUnapprovedGameobject(@PathVariable("id") int id) {
        return GameObjectDtoAssembler.convertToGameObjectDTO(gameObjectService.getUnconfirmedGameObject(id));
    }

    @GetMapping
    public List<GameObjectDTO> getAllObjects(Principal principal) {
        if (principal == null) {
            return GameObjectDtoAssembler.convertToListGameObjectDTO(gameObjectService.getAllAvailableGameobjects());
        }
        else {
            return GameObjectDtoAssembler.convertToListGameObjectDTO(gameObjectService.getAllGameobjects());
        }
    }

    @GetMapping("/my")
    public List<GameObjectDTO> getAllMyGameobjects(Principal principal) throws ResourceNotFoundException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        return GameObjectDtoAssembler.convertToListGameObjectDTO(gameObjectService.getAllMyGameObjects(user));
    }

    @GetMapping("/games")
    public List<GameDTO> getAllGames() {
        return GameDtoAssembler.convertToListGameDTO(gameObjectService.getAllGames());
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
}
