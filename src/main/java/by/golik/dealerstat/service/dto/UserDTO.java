package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Calendar;

/**
 * DTO Model for User
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    /**
     * Regex For Email Validation
     */
    public static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]+$";

    /**
     * Unique identifier
     */
    private int id;

    /**
     * First Name of User
     */
    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^[\\pL '-]+$")
    private String firstName;

    /**
     * Last Name of User
     */
    @NotBlank
    @Pattern(regexp = "[a-zA-Zа-яА-Я]+")
    private String lastName;

    /**
     * Password from user account
     */
    @NotBlank
    private String password;

    /**
     * Email address of user
     */
    @NotBlank
    @Pattern(regexp = EMAIL_REGEX)
    private String email;

    /**
     * Date, when user account created
     */
    private Calendar createdAt;

    /**
     * Date, when user account updated
     */
    private Calendar updatedAt;

    /**
     * Role of user
     */
    private String role;

    /**
     * Rate from user
     */
    private Double rate;
}
