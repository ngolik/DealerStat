package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Integer> {

    Optional<GameObject> findByIdAndApprovedTrue(int id);
    List<GameObject> findAllByAuthor(User author);
    List<GameObject> findAllByApprovedIsTrue();

    @Query("select g from GameObject g where g.status" +
            "= 'AVAILABLE' and g.approved=true")
    List<GameObject> findAllByStatusIsAvailable();

}
