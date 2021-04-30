package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Golik
 */
public class CommentDtoAssembler {

    public static Comment convertToComment(CommentDTO commentDTO, boolean approved,
                                           User user, GameObject gameObject) {
        Comment comment = new Comment();
        comment.setMessage(commentDTO.getMessage());
        comment.setRate(commentDTO.getRate());
        comment.setApproved(approved);
        comment.setGameobject(gameObject);
        comment.setAuthor(user);
        return comment;
    }

    public static CommentDTO convertToCommentDTO(Comment comment) {
        new CommentDTO();
        return CommentDTO.builder().id(comment.getId())
                .message(comment.getMessage()).rate(comment.getRate())
                .createdAt(comment.getCreatedAt()).updatedAt(comment.getUpdatedAt())
                .authorId(comment.getAuthor().getId())
                .gameObjectId(comment.getGameobject().getId()).build();
    }

    public static List<CommentDTO> convertToListCommentDTO(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (Comment comment: comments) {
            commentDTOS.add(convertToCommentDTO(comment));
        }
        return commentDTOS;
    }
}
