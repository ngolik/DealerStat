package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.CommentDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface CommentService {

    void saveWithGameObjectId(Comment comment, Long authorId);
    void approveComment(Comment comment);
    Comment getComment(long id);
    Comment getUnconfirmedComment(long id);
    List<Comment> getAllComments();
    List<Comment> getAllCommentsByAuthor(User user);
    void updateComment(Comment comment, CommentDTO commentDTO, boolean admin);
    HttpStatus update(Comment comment, Long id);
    void deleteComment(Comment comment);
    List<Comment> getAllCommentsByGameObject(GameObject gameObject);

}
