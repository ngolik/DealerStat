package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Calendar;

/**
 * DTO Model for Comment
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    /**
     * Unique identifier
     */
    private int id;

    /**
     * Text message of comment
     */
    private String message;

    /**
     * Rate of {@link by.golik.dealerstat.entity.GameObject}
     */
    @Min(value = 1)
    @Max(value = 5)
    private int rate;

    /**
     * Date when comment has been created
     */
    private Calendar createdAt;

    /**
     * Date when comment has been updated
     */
    private Calendar updatedAt;

    /**
     * Unique identifier of {@link by.golik.dealerstat.entity.GameObject}
     */
    private long gameObjectId;

    /**
     * Unique identifier of user {@link by.golik.dealerstat.entity.User}, who wrote this comment
     */
    private long authorId;
}
