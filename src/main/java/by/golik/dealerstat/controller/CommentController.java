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
 * Class-controller for comments
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

    /**
     * Creation comments
     * @param id - unique identifier of game object
     * @param commentDTO - value object of comment
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException - if object not found
     * @throws NotEnoughRightException - if user doesn't have enough rights
     * @throws ResourceAlreadyExistException - if comment already exists
     */
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

    /**
     * Confirm comment
     * @param id - unique identifier of comment
     * @throws ResourceNotFoundException - if comments doesn't exist
     */
    @PostMapping("comments/{id}/approve")
    public void approveComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        Comment comment = commentService.getUnconfirmedComment(id);

        commentService.approveComment(comment);
    }

    /**
     * To get Unconfirmed comments
     * @param id - unique identifier
     * @return - object value of comment
     * @throws ResourceNotFoundException - if comment doesn't exist
     */
    @GetMapping("comments/{id}/unapproved")
    public CommentDTO getUnapprovedComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        return CommentDtoAssembler.toDto(commentService.getUnconfirmedComment(id));
    }

    /**
     * Get comment by Id
     * @param id - unique identifier
     * @return - value object of comment
     * @throws ResourceNotFoundException - if comment doesn't exist
     */
    @GetMapping("comments/{id}")
    public CommentDTO getComment(@PathVariable("id") int id) throws ResourceNotFoundException {
        return CommentDtoAssembler.toDto(commentService.getComment(id));
    }

    /**
     * Find all comments for game object
     * @param id - unique identifier
     * @return value object of list of comments
     * @throws ResourceNotFoundException - if comment doesn't exist
     */
    @GetMapping("objects/{id}/comments")
    public List<CommentDTO> getAllCommentsByGameObject(@PathVariable("id") int id) throws ResourceNotFoundException {
        GameObject gameObject = gameObjectService.getGameObjectById(id);

        return CommentDtoAssembler.toDtoList(commentService.
                getAllCommentsByGameObject(gameObject));
    }

    /**
     * Find all comments by user id
     * @param id - unique identifier
     * @return - value object of list of comments
     * @throws ResourceNotFoundException - if comment doesn't exist
     */
    @GetMapping("users/{id}/comments")
    public List<CommentDTO> getAllCommentsByAuthor(@PathVariable("id") int id) throws ResourceNotFoundException {
        User user = userService.getUser(id);

        return CommentDtoAssembler.toDtoList(commentService.getAllCommentsByAuthor(user));
    }

    /**
     * Update comment by Id
     * @param id - unique identifier
     * @param commentDTO - value object of comment
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException - if comment doesn't exist
     * @throws NotEnoughRightException - if user doesn't have enough rights
     */
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

    /**
     * To delete a comment
     * @param id - unique identifier
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException - if comment doesn't exist
     * @throws NotEnoughRightException - if user doesn't have enough rights
     */
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

    /**
     * To find all comments
     * @return - list of comments
     */
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
