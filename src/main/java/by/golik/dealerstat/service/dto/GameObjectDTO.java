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
 * DTO Model for Game Object
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameObjectDTO {
    /**
     * Unique identifier
     */
    private int id;

    /**
     * Text title of {@link by.golik.dealerstat.entity.GameObject}
     */
    @NotBlank
    private String title;

    /**
     * Text with description of {@link by.golik.dealerstat.entity.GameObject}
     */
    @NotBlank
    private String text;

    /**
     * Status of {@link by.golik.dealerstat.entity.GameObject}
     */
    @NotBlank
    @Pattern(regexp = "^AVAILABLE$|^SOLD$")
    private String status;

    /**
     * Unique identifier of Game Object Owner {@link by.golik.dealerstat.entity.User}
     */
    private long authorId;

    /**
     * Date when comment has been created
     */
    private Calendar createdAt;

    /**
     * Date when comment has been updated
     */
    private Calendar updatedAt;

    /**
     * List of @{@link by.golik.dealerstat.entity.Game}
     */
    private List<GameDTO> games;
}
