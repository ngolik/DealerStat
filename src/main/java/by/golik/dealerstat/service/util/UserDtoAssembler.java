package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Role;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class- mapper for Dto model User
 * @author Nikita Golik
 */
public class UserDtoAssembler {

    /**
     * This method transfers {@link UserDTO} to {@link User}
     * @param userDTO - value object {@link UserDTO}
     * @param role - role of {@link User}
     * @return - entity model {@link User}
     */
    public static User toEntity(UserDTO userDTO, Role role) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(role);
        return user;
    }

    /**
     * This method transfers {@link User} to {@link UserDTO}
     * @param user - entity model {@link User}
     * @return - value object {@link UserDTO}
     */
    public static UserDTO toDto(User user) {
        return new UserDTO().builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(user.getRole().getName()).rate(user.getRate()).build();
    }

    /**
     * This method transfers list of {@link UserDTO} to list of {@link User}
     * @param users - list of {@link User}
     * @return - list of value objects {@link UserDTO}
     */
    public static List<UserDTO> toDtoList(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user: users) {
            userDTOS.add(toDto(user));
        }
        return userDTOS;
    }
}
