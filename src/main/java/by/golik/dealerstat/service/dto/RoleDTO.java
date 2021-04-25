package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @NotBlank(message = "role shouldn't be empty")
    @Pattern(regexp = "^ROLE_TRADER$|^ROLE_ANON$",
            message = "role should be ROLE_TRADER OR ROLE_ANON")
    private String role;
}
