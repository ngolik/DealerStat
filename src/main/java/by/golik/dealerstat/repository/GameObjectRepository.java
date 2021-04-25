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
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {

    Optional<GameObject> findByIdAndApprovedTrue(long id);

//    @Query("select g from GameObject g where g.status" +
//            "= by.golik.dealerstat.entity.Status.AVAILABLE and g.approved=true")
//    List<GameObject> findAllByStatusIsAvailable();

    List<GameObject> findAllByApprovedIsTrue();

    List<GameObject> findAllByAuthor(User author);

}
