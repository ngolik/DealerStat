package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.CommentService;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.CommentDTO;
import by.golik.dealerstat.service.util.Mapper;
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
        public CommentController(CommentService commentService,
                                 GameObjectService gameObjectService,
                                 UserService userService) {
            this.commentService = commentService;
            this.gameObjectService = gameObjectService;
            this.userService = userService;
        }

    /**
     *
     * @param comment
     * @param objectId
     * @return
     */
    @PostMapping("objects/{objectId}/comments")
    public ResponseEntity<Comment> postWithGameObject(@RequestBody Comment comment, @PathVariable Long objectId) {
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentService.saveWithGameObjectId(comment, objectId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     */
        @PostMapping("comments/{id}/approve")
        public void approveComment(@PathVariable("id") int id) {
            Comment comment = commentService.getUnconfirmedComment(id);

            commentService.approveComment(comment);
        }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("comments/{id}/unapproved")
        public CommentDTO getUnapprovedComment(@PathVariable("id") int id) {
            return Mapper.convertToCommentDTO(commentService.getUnconfirmedComment(id));
        }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("comments/{id}")
        public CommentDTO getComment(@PathVariable("id") int id) {
            return Mapper.convertToCommentDTO(commentService.getComment(id));
        }

    /**
     *
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = this.commentService.getAllComments();
        return generateListResponse(comments);
        }

    @GetMapping("objects/{id}/comments")
    public List<CommentDTO> getAllCommentsByGameObject(@PathVariable("id") int id) {
        GameObject gameObject = gameObjectService.findGameObjectById(id);

        return Mapper.convertToListCommentDTO(commentService.getAllCommentsByGameObject(gameObject));
    }

    /**
     *
     * @param id
     * @return
     */
        @GetMapping("users/{id}/comments")
        public List<CommentDTO> getAllCommentsByAuthor(@PathVariable("id") int id) {
            User user = userService.getUser(id);

            return Mapper.convertToListCommentDTO(commentService.getAllCommentsByAuthor(user));
        }

        @PutMapping("comments/{id}")
        public void updateComment(@PathVariable("id") int id,
                                  @RequestBody @Valid CommentDTO commentDTO,
                                  Principal principal) {
            User user = userService.getUserByEmailAndEnabled(principal.getName());
            Comment comment = commentService.getUnconfirmedComment(id);

            if (!user.equals(comment.getAuthor())) {
//                throw new NotEnoughRightException("You can't rate this post");
            }

            commentService.updateComment(comment, commentDTO, userService.isAdmin(user));
        }

        @DeleteMapping("comments/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteComment(@PathVariable("id") int id, Principal principal) {
            Comment comment = commentService.getUnconfirmedComment(id);
            User user = userService.getUserByEmailAndEnabled(principal.getName());

            if (!userService.isAdmin(user) && !comment.getAuthor().equals(user)) {
//                throw new NotEnoughRightException("You can't delete this comment");
            }

            commentService.deleteComment(comment);
        }

    protected ResponseEntity<List<Comment>> generateListResponse(List<Comment> comments) {
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
