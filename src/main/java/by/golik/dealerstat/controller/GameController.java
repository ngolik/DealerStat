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


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> get(@PathVariable Long id) {
        if (!this.gameService.findByGameId(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Game game = this.gameService.findByGameId(id).get();
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Game>> getAll() {
        List<Game> games = this.gameService.findAllGames();
        return generateListResponse(games);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> post(@RequestBody Game game) {
        HttpHeaders headers = new HttpHeaders();
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.gameService.saveGame(game);
        return new ResponseEntity<>(game, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> put(@RequestBody Game game, @PathVariable Long id) {
        return new ResponseEntity<>(gameService.update(game, id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> delete(@PathVariable Long id) {
        gameService.deleteGameById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected ResponseEntity<List<Game>> generateListResponse(List<Game> games) {
        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

}
