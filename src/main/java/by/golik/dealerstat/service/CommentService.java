package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface CommentService {

    public Optional<Comment> findByCommentId(Long id);
    public List<Comment> findAllComments();
    public void saveComment(Comment comment);
    public void deleteCommentById(Long id);

    public List<Comment> findByAuthorId(Long id);
    public void saveWithGameObjectId(Comment comment, Long authorId);
    public List<Comment> findByTraderId(Long id);
    public List<Comment> findByGameObjectId(Long id);

}
