package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.Role;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

;

/**
 * @author Nikita Golik
 */
public class UserDtoAssembler {

    public static User convertToUser(UserDTO userDTO, Role role) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(role);
        return user;
    }

    public static UserDTO convertToUserDTO(User user) {
        return new UserDTO().builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(user.getRole().getName()).rate(user.getRate()).build();
    }

    public static List<UserDTO> convertToListUserDTO(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user: users) {
            userDTOS.add(convertToUserDTO(user));
        }
        return userDTOS;
    }
}
