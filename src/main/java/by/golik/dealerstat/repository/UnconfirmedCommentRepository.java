package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.UnconfirmedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Nikita Golik
 */
@Repository
public interface UnconfirmedCommentRepository  extends JpaRepository<UnconfirmedComment, Integer> {
    void deleteByComment(Comment comment);
}
