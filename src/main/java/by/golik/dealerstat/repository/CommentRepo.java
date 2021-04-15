package by.golik.dealerstat.repository;

import by.golik.dealerstat.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface CommentRepo extends CrudRepository<Comment, Long> {
    List<Comment> findByTag(String tag);
}
