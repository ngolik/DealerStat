package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTO Model for Game
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    /**
     * Unique identifier of Game
     */
    private int id;

    /**
     * Unique name Of Game
     */
    @NotBlank(message = "name shouldn't be empty")
    private String name;
}
