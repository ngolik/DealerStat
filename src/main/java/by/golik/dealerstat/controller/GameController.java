package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.service.CommentService;
import by.golik.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nikita Golik
 */
@Controller
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "games")
    public String listGames(Model model){
        model.addAttribute("game", new Game());
        model.addAttribute("listGames", this.gameService.listGames());
        return "games";
    }


    @PostMapping(value = "/games/add")
    public String addGame(@ModelAttribute("game") Game game){

        if(game.getId() == 0){
            gameService.addGame(game);
        }else {
            gameService.updateGame(game);
        }

        return "redirect:/games";
    }


    @RequestMapping("/games/remove/{id}")
    public String removeGame(@PathVariable("id") int id){
        gameService.removeGame(id);

        return "redirect:/games";
    }


    @RequestMapping("/games/edit/{id}")
    public String editGame(@PathVariable("id") int id, Model model){
        model.addAttribute("game", gameService.getGameById(id));
        model.addAttribute("listGames", gameService.listGames());

        return "games";
    }
}
