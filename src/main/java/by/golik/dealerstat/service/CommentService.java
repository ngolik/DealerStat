package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.dto.CommentDTO;

import java.util.List;

/**
 * Interface with methods to action with {@link Comment} Comment Entity
 * @author Nikita Golik
 */
public interface CommentService {

    /**
     * This method describes creation of {@link Comment} to {@link GameObject} by {@link User}
     * @param comment - {@link Comment}
     * @param gameObject - {@link GameObject}
     * @param user - {@link User}
     * @throws ResourceAlreadyExistException - if comment already Exist
     */
    void createComment(Comment comment, GameObject gameObject, User user) throws ResourceAlreadyExistException;

    /**
     * Admin root allows to confirm {@link Comment} which written by User
     * @param comment - created comment {@link Comment}
     */
    void approveComment(Comment comment);

    /**
     * This method allows to find comment using it's unique identifier
     * @param id - unique identifier
     * @return - entity model comment
     * @throws ResourceNotFoundException - if comment doesn't exist
     */
    Comment getComment(int id) throws ResourceNotFoundException;

    /**
     * This method allows to find comment that doesn't confirm by admin using comment unique identifier
     * @param id - unique identifier of unconfirmed comment
     * @return - entity model Unconfirmed Comment
     * @throws ResourceNotFoundException - if comment doesn't exist
     */
    Comment getUnconfirmedComment(int id) throws ResourceNotFoundException;

    /**
     * This method allows to find all comments
     * @return - list of comments
     */
    List<Comment> getAllComments();

    /**
     * This method allows to find all comments for chosen Game Object
     * @param gameObject - chosen Game Object
     * @return - list of comments
     */
    List<Comment> getAllCommentsByGameObject(GameObject gameObject);

    /**
     * This method allows to find all comments for chosen User(author of comment)
     * @param user - author of comment
     * @return - list of comments
     */
    List<Comment> getAllCommentsByAuthor(User user);

    /**
     * This method allows to update comment using define root
     * @param comment - entity model of written comment
     * @param commentDTO - value object of Comment
     * @param admin - true, if user has admin root; else - false
     */
    void updateComment(Comment comment, CommentDTO commentDTO, boolean admin);

    /**
     * This method allow to delete comment
     * @param comment - entity model of written comment
     */
    void deleteComment(Comment comment);
}
