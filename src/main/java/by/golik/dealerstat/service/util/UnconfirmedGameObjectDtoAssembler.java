package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.Status;
import by.golik.dealerstat.entity.UnconfirmedGameObject;
import by.golik.dealerstat.service.dto.GameObjectDTO;

import java.util.List;

/**
 * This class - mapper for Dto model Unconfirmed Game Object
 * @author Nikita Golik
 */
public class UnconfirmedGameObjectDtoAssembler {

    /**
     * This method transfers {@link GameObjectDTO} to {@link UnconfirmedGameObject}
     * @param gameObjectDTO - value object {@link GameObjectDTO}
     * @param games - list of {@link Game}
     * @param gameObject - entity model {@link GameObject}
     * @return - entity model {@link UnconfirmedGameObject}
     */
    public static UnconfirmedGameObject toEntity(GameObjectDTO gameObjectDTO,
                                                 List<Game> games, GameObject gameObject) {
        UnconfirmedGameObject unconfirmedGameObject = new UnconfirmedGameObject();
        unconfirmedGameObject.setTitle(gameObjectDTO.getTitle());
        unconfirmedGameObject.setText(gameObjectDTO.getText());
        unconfirmedGameObject.setStatus(Status.valueOf(gameObjectDTO.getStatus()));
        unconfirmedGameObject.setGames(games);
        unconfirmedGameObject.setGameobject(gameObject);
        return unconfirmedGameObject;
    }
}
