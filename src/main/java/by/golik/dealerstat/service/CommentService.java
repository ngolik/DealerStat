package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.dto.CommentDTO;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface CommentService {

    void createComment(Comment comment, GameObject gameObject, User user) throws ResourceAlreadyExistException;
    void approveComment(Comment comment);
    Comment getComment(int id) throws ResourceNotFoundException;
    Comment getUnconfirmedComment(int id) throws ResourceNotFoundException;
    List<Comment> getAllCommentsByGameObject(GameObject gameObject);
    List<Comment> getAllCommentsByAuthor(User user);
    void updateComment(Comment comment, CommentDTO commentDTO, boolean admin);
    void deleteComment(Comment comment);

}
