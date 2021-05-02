package by.golik.dealerstat.service.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO Model for Authentication data
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    /**
     * Login (username) of User {@link by.golik.dealerstat.entity.User}
     */
    @NotBlank
    @Email
    private String login;

    /**
     * Password from account of User {@link by.golik.dealerstat.entity.User}
     */
    @NotBlank
    private String password;
}
