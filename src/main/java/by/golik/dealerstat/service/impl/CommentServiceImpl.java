package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.UnconfirmedComment;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.repository.CommentRepository;
import by.golik.dealerstat.repository.UnconfirmedCommentRepository;
import by.golik.dealerstat.service.CommentService;
import by.golik.dealerstat.service.dto.CommentDTO;
import by.golik.dealerstat.service.util.UnconfirmedCommentDtoAssembler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
@Service
@Transactional
@Log4j2
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UnconfirmedCommentRepository unconfirmedCommentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              UnconfirmedCommentRepository unconfirmedCommentRepository) {
        this.commentRepository = commentRepository;
        this.unconfirmedCommentRepository = unconfirmedCommentRepository;
    }

    @Override
    public void createComment(Comment comment, GameObject gameObject, User user) throws ResourceAlreadyExistException {
        if (commentRepository.existsByAuthorAndGameobject(user, gameObject)) {
            log.info("Comment " + comment + " already exist!");
            throw new ResourceAlreadyExistException(
                    "Comment with this author and post already exist!");
        }
        commentRepository.save(comment);
        log.info("Comment " + comment + " has been created.");
    }

    @Override
    public void approveComment(Comment comment) {
        comment.setApproved(true);
        if (comment.getUnconfirmedComment() != null){
            unconfirmedCommentRepository.delete(comment.getUnconfirmedComment());
        }
        log.info("Comment " + comment + " has been successfully approved.");
        commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(int id) throws ResourceNotFoundException {
        Optional<Comment> optionalComment = commentRepository
                .findById(id);
        Comment comment;

        if (!optionalComment.isPresent()) {
            log.info("Comment with  id " + id + " doesn't exist!");
            throw new ResourceNotFoundException("This comment doesn't exist!");
        }
        comment = optionalComment.get();
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getUnconfirmedComment(int id) throws ResourceNotFoundException {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        Comment comment;

        if (!optionalComment.isPresent()) {
            log.info("Comment with  id " + id + " doesn't exist!");
            throw new ResourceNotFoundException("This comment doesn't exist!");
        }
        comment = optionalComment.get();
        if (comment.getUnconfirmedComment() != null) {
            UnconfirmedComment unconfirmedComment = comment.getUnconfirmedComment();

            comment.setMessage(unconfirmedComment.getMessage());
            comment.setRate(unconfirmedComment.getRate());
        }
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByGameObject(GameObject gameObject) {
        return commentRepository.findAllByGameobject(gameObject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByAuthor(User user) {
        return commentRepository.findAllByAuthor(user);
    }

    @Override
    public void updateComment(Comment comment, CommentDTO commentDTO,
                              boolean admin) {
        if (admin || !comment.isApproved()) {
            comment.setMessage(commentDTO.getMessage());
            comment.setRate(commentDTO.getRate());
        } else {
            UnconfirmedComment unapprovedComment = UnconfirmedCommentDtoAssembler
                    .toEntity(commentDTO, comment);

            unconfirmedCommentRepository.deleteByComment(comment);
            unconfirmedCommentRepository.save(unapprovedComment);
        }
        log.info("Comment " + comment + " has been updated.");
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        log.info("Comment " + comment + " has been deleted.");
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
