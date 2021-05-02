package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO Model for Email
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    /**
     * Email address of {@link by.golik.dealerstat.entity.User}
     */
    @NotBlank
    @Email
    private String email;
}
