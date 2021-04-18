package by.golik.dealerstat.repository.impl;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.repository.CommentRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Nikita Golik
 */
@Repository
@Transactional
public class CommentRepoImpl implements CommentRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentRepoImpl.class);

    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addComment(Comment comment) {
        comment.setCreatedAt(new Date());
        getSession().persist(comment);
        LOGGER.info("Comment successfully saved. Comment details: " + comment);
    }

    @Override
    public void updateComment(Comment comment) {
        getSession().update(comment);
        LOGGER.info("Comment successfully updated. Comment details: " + comment);
    }

    @Override
    public void removeComment(Integer id) {
        Comment comment = (Comment) getSession().load(Comment.class, id);

        if(comment !=null){
            getSession().delete(comment);
        }
        LOGGER.info("Comment successfully removed. Comment details: " + comment);
    }

    @Override
    public Comment getCommentById(Integer id) {
        Comment comment = (Comment) getSession().load(Comment.class, id);
        LOGGER.info("Comment successfully loaded. Comment details: " + comment);
        return comment;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> listComments() {
        List<Comment> comments = getSession().createQuery("from Comment ORDER BY rate ASC").list();
        for(Comment comment : comments){
            LOGGER.info("Comment list: " + comment);
        }
        return comments;
    }
}
