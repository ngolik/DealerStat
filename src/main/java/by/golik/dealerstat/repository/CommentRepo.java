package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Comment;
import java.util.List;

/**
 * @author Nikita Golik
 */
public interface CommentRepo {
    void addComment(Comment comment);

    void updateComment(Comment comment);

    void removeComment(Integer id);

    Comment getCommentById(Integer id);

    List<Comment> listComments();
}
