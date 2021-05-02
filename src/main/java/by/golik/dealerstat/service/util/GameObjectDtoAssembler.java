package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.Status;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class - Mapper for Dto Model of Game Object
 * @author Nikita Golik
 */
public class GameObjectDtoAssembler {

    /**
     * This method transfers {@link GameObjectDTO} to {@link GameObject}
     * @param gameObjectDTO - value object {@link GameObjectDTO}
     * @param approved - state {@link GameObject}
     * @param user - Trader {@link User}
     * @param games - list of {@link Game}
     * @return - Entity model {@link GameObject}
     */
    public static GameObject toEntity(GameObjectDTO gameObjectDTO, boolean approved,
                                      User user, List<Game> games) {
        new GameObject();
        return GameObject.builder().title(gameObjectDTO.getTitle())
                .text(gameObjectDTO.getText()).status(Status.valueOf(gameObjectDTO.getStatus()))
                .approved(approved).user(user).games(games).build();
    }

    /**
     * This method transfers {@link GameObject} to {@link GameObjectDTO}
     * @param gameObject - entity model {@link GameObject}
     * @return - value object {@link GameObjectDTO}
     */
    public static GameObjectDTO toDto(GameObject gameObject) {
        List<GameDTO> gamesDTO = GameDtoAssembler.toDtoList(gameObject.getGames());

        new GameObjectDTO();
        return GameObjectDTO.builder().id(gameObject.getId())
                .title(gameObject.getTitle()).text(gameObject.getText())
                .status(gameObject.getStatus().name())
                .authorId(gameObject.getAuthor().getId())
                .createdAt(gameObject.getCreatedAt())
                .updatedAt(gameObject.getUpdatedAt())
                .games(gamesDTO).build();
    }

    /**
     * This method transfers list of {@link GameObject} to list of {@link GameObjectDTO}
     * @param gameObjects - list of {@link GameObject}
     * @return - list of {@link GameObjectDTO}
     */
    public static List<GameObjectDTO> toDtoList(List<GameObject> gameObjects) {
        List<GameObjectDTO> postDTOS = new ArrayList<>();

        for (GameObject gameObject: gameObjects) {
            postDTOS.add(toDto(gameObject));
        }
        return postDTOS;
    }
}
