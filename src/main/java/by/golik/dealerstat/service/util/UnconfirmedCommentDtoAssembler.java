package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.UnconfirmedComment;
import by.golik.dealerstat.service.dto.CommentDTO;

/**
 * This class - mapper for Dto Model Unconfirmed Comment
 * @author Nikita Golik
 */
public class UnconfirmedCommentDtoAssembler {

    /**
     * This method transfers {@link CommentDTO} to {@link UnconfirmedComment}
     * @param commentDTO - value object {@link CommentDTO}
     * @param comment - {@link Comment}
     * @return - entity model {@link UnconfirmedComment}
     */
    public static UnconfirmedComment toEntity(CommentDTO commentDTO,
                                              Comment comment) {
        UnconfirmedComment unconfirmedComment = new UnconfirmedComment();
        unconfirmedComment.setMessage(commentDTO.getMessage());
        unconfirmedComment.setRate(commentDTO.getRate());
        unconfirmedComment.setComment(comment);
        return unconfirmedComment;
    }
}
