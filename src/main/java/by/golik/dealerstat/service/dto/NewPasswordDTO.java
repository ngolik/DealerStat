package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO Model for New Password
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDTO {

    /**
     * Code for confirmation restoring password
     */
    @NotBlank
    @Digits(integer = 4, fraction = 0)
    @Size(min = 4, max = 4)
    private String code;

    /**
     * Email address of User {@link by.golik.dealerstat.entity.User}
     */
    @NotBlank
    @Email
    private String email;

    /**
     * New restored password
     */
    @NotBlank
    private String newPassword;
}
