package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface GameObjectService {

    public Optional<GameObject> findByGameObjectId(Long id);
    public List<GameObject> findAllGameObjects();
    public void saveGameObject(GameObject gameObject);
    public void deleteGameObjectById(Long id);
    HttpStatus update(GameObject gameObject, Long id);

    List<GameObject> findAllByOwner_Id(Long id);
    List<GameObject> findAllByGame_Id(Long id);
    List<GameObject> findAllByGame_Name(String name);
}
