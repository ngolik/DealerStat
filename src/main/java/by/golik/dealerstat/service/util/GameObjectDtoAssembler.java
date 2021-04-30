package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.Status;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;

import java.util.ArrayList;
import java.util.List;

import static by.golik.dealerstat.service.util.GameDtoAssembler.convertToListGameDTO;

/**
 * @author Nikita Golik
 */
public class GameObjectDtoAssembler {
    public static GameObject convertToGameObject(GameObjectDTO gameObjectDTO, boolean approved,
                                                 User user, List<Game> games) {
        new GameObject();
        return GameObject.builder().title(gameObjectDTO.getTitle())
                .text(gameObjectDTO.getText()).status(Status.valueOf(gameObjectDTO.getStatus()))
                .approved(approved).user(user).games(games).build();
    }

    public static GameObjectDTO convertToGameObjectDTO(GameObject gameObject) {
        List<GameDTO> gameDTOS = convertToListGameDTO(gameObject.getGames());

        new GameObjectDTO();
        return GameObjectDTO.builder().id(gameObject.getId())
                .title(gameObject.getTitle()).text(gameObject.getText())
                .status(gameObject.getStatus().name())
                .authorId(gameObject.getAuthor().getId())
                .createdAt(gameObject.getCreatedAt())
                .updatedAt(gameObject.getUpdatedAt())
                .games(gameDTOS).build();
    }
    public static List<GameObjectDTO> convertToListGameObjectDTO(List<GameObject> gameObjects) {
        List<GameObjectDTO> postDTOS = new ArrayList<>();

        for (GameObject gameObject: gameObjects) {
            postDTOS.add(convertToGameObjectDTO(gameObject));
        }
        return postDTOS;
    }
}
