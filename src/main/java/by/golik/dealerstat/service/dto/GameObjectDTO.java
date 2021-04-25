package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Calendar;
import java.util.List;

/**
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameObjectDTO {
    private long id;

    @NotBlank(message = "title shouldn't be empty")
    private String title;

    @NotBlank(message = "text shouldn't be empty")
    private String text;

    @NotBlank(message = "status shouldn't be empty")
    @Pattern(regexp = "^PUBLIC$|^DRAFT$",
            message = "status should be PUBLIC OR DRAFT")
    private String status;

    private long authorId;

    private Calendar createdAt;

    private Calendar updatedAt;

    private List<GameDTO> games;
}
