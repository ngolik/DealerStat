package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nikita Golik
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByAuthorAndGameobject(User author, GameObject gameobject);

    List<Comment> findAllByGameobject(GameObject gameObject);
    List<Comment> findAllByAuthor(User author);

}
