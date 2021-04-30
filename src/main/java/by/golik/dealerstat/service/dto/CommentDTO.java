package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Calendar;

/**
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private int id;

    private String message;

    @Min(value = 1, message = "rating shouldn't be less than 1")
    @Max(value = 5, message = "rating shouldn't be more than 5")
    private int rate;

    private Calendar createdAt;

    private Calendar updatedAt;

    private long gameObjectId;

    private long authorId;
}
