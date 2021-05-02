package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.NotEnoughRightException;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.CommentService;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.CommentDTO;
import by.golik.dealerstat.service.util.CommentDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author Nikita Golik
 */

@RestController
@RequestMapping("/")
public class CommentController {

      private final CommentService commentService;

      private final GameObjectService gameObjectService;

      private final UserService userService;

    @Autowired
    public CommentController(GameObjectService gameObjectService, UserService userService,
                             CommentService commentService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
        this.commentService = commentService;
    }
    @PostMapping("/objects/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@PathVariable("id") int id,
                              @RequestBody @Valid CommentDTO commentDTO,
                              Principal principal) throws ResourceNotFoundException, NotEnoughRightException,
            ResourceAlreadyExistException {

        User user = userService.getUserByEmailAndEnabled(principal.getName());
        GameObject post = gameObjectService.getGameObjectById(id);
        Comment comment;

        if (user.equals(post.getAuthor())) {
            throw new NotEnoughRightException("You can't rate this post");
        }
        comment = CommentDtoAssembler.toEntity(commentDTO,userService.isAdmin(user),
                user, post);
        commentService.createComment(comment, post, user);
    }

    @PostMapping("comments/{id}/approve")
    public void approveComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        Comment comment = commentService.getUnconfirmedComment(id);

        commentService.approveComment(comment);
    }

    @GetMapping("comments/{id}/unapproved")
    public CommentDTO getUnapprovedComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        return CommentDtoAssembler.toDto(commentService.getUnconfirmedComment(id));
    }

    @GetMapping("comments/{id}")
    public CommentDTO getComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        return CommentDtoAssembler.toDto(commentService.getComment(id));
    }

    @GetMapping("objects/{id}/comments")
    public List<CommentDTO> getAllCommentsByPost(@PathVariable("id") int id) throws ResourceNotFoundException {
        GameObject gameObject = gameObjectService.getGameObjectById(id);

        return CommentDtoAssembler.toDtoList(commentService.
                getAllCommentsByGameObject(gameObject));
    }

    @GetMapping("users/{id}/comments")
    public List<CommentDTO> getAllCommentsByAuthor(@PathVariable("id") int id) throws ResourceNotFoundException {
        User user = userService.getUser(id);

        return CommentDtoAssembler.toDtoList(commentService.getAllCommentsByAuthor(user));
    }

    @PutMapping("comments/{id}")
    public void updateComment(@PathVariable("id") int id,
                              @RequestBody @Valid CommentDTO commentDTO,
                              Principal principal) throws ResourceNotFoundException, NotEnoughRightException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());
        Comment comment = commentService.getUnconfirmedComment(id);

        if (!user.equals(comment.getAuthor())) {
            throw new NotEnoughRightException("You can't rate this post");
        }
        commentService.updateComment(comment, commentDTO, userService.isAdmin(user));
    }

    @DeleteMapping("comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") int id, Principal principal) throws ResourceNotFoundException,
            NotEnoughRightException {
        Comment comment = commentService.getUnconfirmedComment(id);
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        if (!userService.isAdmin(user) && !comment.getAuthor().equals(user)) {
            throw new NotEnoughRightException("You can't delete this comment");
        }
        commentService.deleteComment(comment);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = this.commentService.getAllComments();
        return generateListResponse(comments);
    }

    protected ResponseEntity<List<Comment>> generateListResponse(List<Comment> comments) {
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
