package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Game;
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
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Method to find game by Id
     * @param id - unique identifier
     * @return  - a wrapper for the response and additionally for the HTTP headers and status code
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> get(@PathVariable int id) {
        if (!this.gameService.findByGameId(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Game game = this.gameService.findByGameId(id).get();
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    /**
     * Method to find all games
     * @return - a wrapper for the response and additionally for the HTTP headers and status code
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Game>> getAll() {
        List<Game> games = this.gameService.findAllGames();
        return generateListResponse(games);
    }

    /**
     * Method to create game
     * @param game - entity model of game
     * @return - a wrapper for the response and additionally for the HTTP headers and status code
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        HttpHeaders headers = new HttpHeaders();
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.gameService.saveGame(game);
        return new ResponseEntity<>(game, headers, HttpStatus.CREATED);
    }

    /**
     * Method to update game
     * @param game - entity model of game
     * @param id - unique identifier
     * @return - a wrapper for the response and additionally for the HTTP headers and status code
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> updateGame(@RequestBody Game game, @PathVariable int id) {
        return new ResponseEntity<>(gameService.update(game, id));
    }

    /**
     * Method to delete game
     * @param id - unique identifier
     * @return - a wrapper for the response and additionally for the HTTP headers and status code
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> delete(@PathVariable int id) {
        gameService.deleteGameById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method-helper for creation wrapper for the response and additionally for the HTTP headers and status code
     * @param games - lsit of games
     * @return - wrapper for the response and additionally for the HTTP headers and status code
     */
    protected ResponseEntity<List<Game>> generateListResponse(List<Game> games) {
        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}
