package by.golik.dealerstat.controller;

import by.golik.dealerstat.repository.CommentDao;
import by.golik.dealerstat.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

/**
 * @author Nikita Golik
 */
@Controller
public class MainController {


    private CommentDao commentDao;

    @Autowired
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Comment> comments = commentDao.findAll();
        model.put("comments", comments);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String message, @RequestParam Integer rate,
                      @RequestParam Boolean approved, @RequestParam Date createdAt,
                      Map<String, Object> model) {
        Comment comment = new Comment(message, rate, approved, createdAt);
        commentDao.save(comment);

        Iterable<Comment> comments = commentDao.findAll();
        model.put("comments", comments);

        return "main";
    }
}
