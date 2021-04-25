package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.UnconfirmedComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita Golik
 */
public interface UnconfirmedCommentRepository  extends JpaRepository<UnconfirmedComment, Long> {
    void deleteByComment(Comment comment);
}
