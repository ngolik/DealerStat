package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String token;
}
