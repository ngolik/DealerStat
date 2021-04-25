package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Calendar;

/**
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private long id;

    @NotBlank(message = "firstName shouldn't be empty")
    @Pattern(regexp = "[a-zA-Zа-яА-Я]+",
            message = "firstName should includes only letters")
    private String firstName;

    @NotBlank(message = "lastName shouldn't be empty")
    @Pattern(regexp = "[a-zA-Zа-яА-Я]+",
            message = "lastName should includes only letters")
    private String lastName;

    @NotBlank(message = "password shouldn't be empty")
    private String password;

    @NotBlank(message = "email shouldn't be empty")
    @Email(message = "field email should be email")
    private String email;

    private Calendar createdAt;

    private Calendar updatedAt;

    private String role;

    private Double rate;
}
