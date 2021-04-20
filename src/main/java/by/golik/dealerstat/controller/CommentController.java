package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nikita Golik
 */

@RestController
@RequestMapping("/")
public class CommentController {

    private final CommentService commentService;

    public CommentService getCommentService() {
        return commentService;
    }

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/{authorId}/written-comments")
    public ResponseEntity<List<Comment>> getCommentsByAuthor(@PathVariable Long authorId) {
        List<Comment> comments = getCommentService().findByAuthorId(authorId);
        return generateListResponse(comments);
    }

    @GetMapping("{userId}/received-comments/")
    public ResponseEntity<List<Comment>> getCommentsByGameObjectOwnerId(@PathVariable Long userId) {
        List<Comment> comments = getCommentService().findByTraderId(userId);
        return generateListResponse(comments);
    }

    @PostMapping("objects/{objectId}/comments")
    public ResponseEntity<Comment> postWithGameObject(@RequestBody Comment comment, @PathVariable Long objectId) {
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        getCommentService().saveWithGameObjectId(comment, objectId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    protected ResponseEntity<List<Comment>> generateListResponse(List<Comment> comments) {
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
