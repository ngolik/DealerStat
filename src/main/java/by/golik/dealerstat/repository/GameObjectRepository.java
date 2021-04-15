package by.golik.dealerstat.repository;

import by.golik.dealerstat.model.GameObject;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface GameObjectRepository {
    List<GameObject> findAllByOwner_Id(Long id);
    List<GameObject> findAllByGame_Id(Long Id);
    List<GameObject> findAllByGame_Name(String name);
}
