package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.UnconfirmedComment;
import by.golik.dealerstat.service.dto.CommentDTO;

/**
 * @author Nikita Golik
 */
public class UnconfirmedCommentDtoAssembler {
    public static UnconfirmedComment convertToUnconfirmedComment(CommentDTO commentDTO,
                                                                 Comment comment) {
        UnconfirmedComment unconfirmedComment = new UnconfirmedComment();
        unconfirmedComment.setMessage(commentDTO.getMessage());
        unconfirmedComment.setRate(commentDTO.getRate());
        unconfirmedComment.setComment(comment);
        return unconfirmedComment;
    }
}
