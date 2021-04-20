package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.repository.CommentRepository;
import by.golik.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> findByCommentId(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public HttpStatus update(Comment comment, Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()) {
            return HttpStatus.NOT_FOUND;
        }
        comment.setId(id);
        commentRepository.save(comment);
        return HttpStatus.OK;
    }

    @Override
    public List<Comment> findByAuthorId(Long id) {
        return commentRepository.findAllByAuthor_Id(id);
    }

    @Override
    public void saveWithGameObjectId(Comment comment, Long authorId) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByTraderId(Long id) {
        return commentRepository.findAllByGameObject_Owner_Id(id);
    }

    @Override
    public List<Comment> findByGameObjectId(Long id) {
        return commentRepository.findAllByGameObject_Id(id);
    }
}
