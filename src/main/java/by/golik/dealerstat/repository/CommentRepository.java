package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nikita Golik
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findAllByAuthor_Id(Long Id);
    public List<Comment> findAllByGameObject_Id(Long Id);
    public List<Comment> findAllByGameObject_Owner_Id(Long id);

}
