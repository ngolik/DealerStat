package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nikita Golik
 */
@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {

    List<GameObject> findAllByOwner_Id(Long id);
    List<GameObject> findAllByGame_Id(Long Id);
    List<GameObject> findAllByGame_Name(String name);

}
