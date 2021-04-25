package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDTO {

    @NotBlank(message = "code shouldn't be empty")
    @Digits(message = "code should be digit", integer = 4, fraction = 0)
    @Size(min = 4, max = 4, message = "code should consist 4 digits")
    private String code;

    @NotBlank(message = "email shouldn't be empty")
    @Email(message = "field email should be email")
    private String email;

    @NotBlank(message = "newPassword shouldn't be empty")
    private String newPassword;
}
