package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class - Mapper for Dto Model Comment
 * @author Nikita Golik
 */
public class CommentDtoAssembler {

    /**
     * This method transfers {@link CommentDTO} to {@link Comment}
     * @param commentDTO - value-object {@link CommentDTO}
     * @param approved - state of comment
     * @param user - who creates the comment {@link User}
     * @param gameObject - game object {@link GameObject} to which a comment was made
     * @return - Entity Object {@link Comment}
     */
    public static Comment toEntity(CommentDTO commentDTO, boolean approved,
                                   User user, GameObject gameObject) {
        Comment comment = new Comment();
        comment.setMessage(commentDTO.getMessage());
        comment.setRate(commentDTO.getRate());
        comment.setApproved(approved);
        comment.setGameobject(gameObject);
        comment.setAuthor(user);
        return comment;
    }

    /**
     * This method transfers {@link Comment} to {@link CommentDTO}
     * @param comment - Entity Object {@link Comment}
     * @return - Value object {@link CommentDTO}
     */
    public static CommentDTO toDto(Comment comment) {
        new CommentDTO();
        return CommentDTO.builder().id(comment.getId())
                .message(comment.getMessage()).rate(comment.getRate())
                .createdAt(comment.getCreatedAt()).updatedAt(comment.getUpdatedAt())
                .authorId(comment.getAuthor().getId())
                .gameObjectId(comment.getGameobject().getId()).build();
    }

    /**
     * This method transfers list of {@link Comment} to list of {@link CommentDTO}
     * @param comments - list of Entity {@link Comment}
     * @return list of {@link CommentDTO}
     */
    public static List<CommentDTO> toDtoList(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (Comment comment: comments) {
            commentDTOS.add(toDto(comment));
        }
        return commentDTOS;
    }
}
