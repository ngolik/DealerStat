package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.service.dto.GameDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Golik
 */
public class GameDtoAssembler {

    public static List<GameDTO> convertToListGameDTO(List<Game> games) {
        List<GameDTO> gameDTOS = new ArrayList<>();

        games.forEach(game ->
                gameDTOS.add(new GameDTO(game.getId(), game.getName())));
        return gameDTOS;
    }
}
