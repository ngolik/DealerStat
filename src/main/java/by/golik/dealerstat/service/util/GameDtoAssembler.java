package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.service.dto.GameDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class - Mapper for Dto Model Game
 * @author Nikita Golik
 */
public class GameDtoAssembler {

    /**
     * This method transfers list of {@link Game} to list of {@link Game}
     * @param games - list of {@link Game}
     * @return - list of {@link Game}
     */
    public static List<GameDTO> toDtoList(List<Game> games) {
        List<GameDTO> gameDTOS = new ArrayList<>();

        games.forEach(game ->
                gameDTOS.add(new GameDTO(game.getId(), game.getName())));
        return gameDTOS;
    }
}
