package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.repository.CommentRepo;
import by.golik.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nikita Golik
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepo commentRepo;

    @Autowired
    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public void addComment(Comment comment) {
        this.commentRepo.addComment(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        this.commentRepo.updateComment(comment);
    }

    @Override
    public void removeComment(Integer id) {
        this.commentRepo.removeComment(id);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return this.commentRepo.getCommentById(id);
    }

    @Override
    public List<Comment> listComments() {
        return this.commentRepo.listComments();
    }
}
