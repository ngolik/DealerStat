package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Comments
 * @author Nikita Golik
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    boolean existsByAuthorAndGameobject(User author, GameObject gameobject);
    Optional<Comment> findByIdAndApprovedTrue(int id);
    List<Comment> findAllByGameobject(GameObject gameobject);
    List<Comment> findAllByAuthor(User author);

}
