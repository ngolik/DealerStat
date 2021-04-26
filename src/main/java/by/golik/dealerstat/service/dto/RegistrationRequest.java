package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    @NotBlank(message = "email shouldn't be empty")
    @Email(message = "field email should be email")
    private String login;

    @NotBlank(message = "password shouldn't be empty")
    private String password;
}
