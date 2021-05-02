package by.golik.dealerstat.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Model for Token Registration
 * @author Nikita Golik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    /**
     * Token that creates with User authentication
     */
    private String token;
}
