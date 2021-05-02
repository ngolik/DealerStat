package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * DTO Model for Role
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    /**
     * Role Of User {@link by.golik.dealerstat.entity.User}
     */
    @NotBlank
    @Pattern(regexp = "^ROLE_TRADER$|^ROLE_ANON$")
    private String role;
}
