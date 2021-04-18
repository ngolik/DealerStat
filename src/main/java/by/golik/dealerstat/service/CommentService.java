package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Comment;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface CommentService {
    void addComment(Comment comment);

    void updateComment(Comment comment);

    void removeComment(Integer id);

    Comment getCommentById(Integer id);

    List<Comment> listComments();
}
