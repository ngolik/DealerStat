package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.NotEnoughRightException;
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
import java.util.Optional;

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
      public CommentController(CommentService commentService,
                                 GameObjectService gameObjectService,
                                 UserService userService) {
            this.commentService = commentService;
            this.gameObjectService = gameObjectService;
            this.userService = userService;
      }

    /**
     * работает нормально
     * @param comment
     * @param objectId
     * @return
     */
    @PostMapping(value = "objects/{objectId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> postWithGameObject(@PathVariable Long objectId,
        @RequestBody @Valid CommentDTO commentDTO, Principal principal) throws ResourceNotFoundException, NotEnoughRightException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());
        GameObject post = gameObjectService.findGameObjectById(objectId);
        Comment comment;

        if (user.equals(post.getAuthor())) {
            throw new NotEnoughRightException("You can't rate this post");
        }
        comment = CommentDtoAssembler.convertToComment(commentDTO,userService.isAdmin(user),
                user, post);
        commentService.saveWithGameObjectId(comment, post, user);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    /**
     * работает нормально
     * @param id
     */
     @PostMapping(value = "comments/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
     public void approveComment(@PathVariable("id") int id) {
            Comment comment = commentService.getUnconfirmedComment(id);

            commentService.approveComment(comment);
        }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "comments/{id}/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getUnapprovedComment(@PathVariable("id") int id) {
        Comment comment = commentService.getUnconfirmedComment(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
        }

    /**
     * рабюотает нормально
     * @param id
     * @return
     */
    @GetMapping(value = "comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getComment(@PathVariable("id") int id) {
        Comment comment = commentService.getComment(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     * работает нормально
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = this.commentService.getAllComments();
        return generateListResponse(comments);
    }

    @GetMapping("objects/{id}/comments")
    public List<CommentDTO> getAllCommentsByGameObject(@PathVariable("id") int id) {
        Optional<GameObject> gameObject = gameObjectService.findGameObjectById(id);

        return CommentDtoAssembler.convertToListCommentDTO(commentService.getAllCommentsByGameObject(gameObject));
    }

//    /**
//     * работает нормально
//     * @param id
//     * @return
//     */
//     @GetMapping(value = "users/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<List<Comment>> getAllCommentsByAuthor(@PathVariable("id") int id) throws ResourceNotFoundException {
//        User user = userService.getUser(id);
//        List<Comment> commentList = commentService.getAllCommentsByAuthor(user);
//        return generateListResponse(commentList);
//     }

    /**
     * todo нужна проверка на юзера
     * @param comment
     * @param id
     * @return
     */
     @PutMapping(value = "comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable Long id) {
         return new ResponseEntity<>(commentService.update(comment, id));
     }

    /**
     * работает нормально
     * todo нужна проверка на юзера
     * @param id
     * @return
     */
     @DeleteMapping(value = "comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Comment> deleteComment(@PathVariable("id") long id) {
         Comment comment = commentService.getUnconfirmedComment(id);
         commentService.deleteComment(comment);
         return new ResponseEntity<>(HttpStatus.OK);
     }

    protected ResponseEntity<List<Comment>> generateListResponse(List<Comment> comments) {
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
