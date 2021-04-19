package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Nikita Golik
 */
@Controller
public class GameController {
    GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public ModelAndView allGames() {
        List<Game> games = gameService.allGames();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("games");
        modelAndView.addObject("gamesList", games);
        return modelAndView;
    }

    @RequestMapping(value = "/games/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        return modelAndView;
    }

    @RequestMapping(value = "/games/add", method = RequestMethod.POST)
    public ModelAndView addGame(@ModelAttribute("game") Game game) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/games");
        gameService.addGame(game);
        return modelAndView;
    }

    @RequestMapping(value = "/games/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id) {
        Game game = gameService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("game", game);
        return modelAndView;
    }

    @RequestMapping(value = "/games/edit", method = RequestMethod.POST)
    public ModelAndView editGame(@ModelAttribute("game") Game game) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/games");
        gameService.editGame(game);
        return modelAndView;
    }

    @RequestMapping(value = "/games/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteGame(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/games");
        Game game = gameService.getById(id);
        gameService.deleteGame(game);
        return modelAndView;
    }
}
