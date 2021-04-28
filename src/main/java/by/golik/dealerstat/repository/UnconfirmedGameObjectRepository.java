package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.UnconfirmedGameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Nikita Golik
 */
@Repository
public interface UnconfirmedGameObjectRepository extends JpaRepository<UnconfirmedGameObject, Long> {
    void deleteByGameobject(GameObject gameobject);
}
