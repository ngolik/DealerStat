package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.Status;
import by.golik.dealerstat.entity.UnconfirmedGameObject;
import by.golik.dealerstat.service.dto.GameObjectDTO;

import java.util.List;

/**
 * @author Nikita Golik
 */
public class UnconfirmedGameObjectDtoAssembler {
    public static UnconfirmedGameObject convertToUnconfirmedGameObject(GameObjectDTO gameObjectDTO,
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
