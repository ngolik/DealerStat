package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.RoleDTO;
import by.golik.dealerstat.service.dto.UserDTO;
import by.golik.dealerstat.service.util.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Golik
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") int id, Principal principal) {
        User user = userService.getUser(id);
        User principalUser = userService.getUserByEmailAndEnabled(principal.getName());
        UserDTO userDTO;

//        userService.calculateRating(user);
        userDTO = Mapper.convertToUserDTO(user);
        if (userService.isAdmin(principalUser) || principalUser.getId() == id) {
            userDTO.setEmail(user.getEmail());
        }
        return userDTO;
    }

    /**
     *
     * @return
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return Mapper.convertToListUserDTO(userService.getAllUsers());
    }

    /**
     *
     * @return
     */
    @GetMapping("/readers")
    public List<UserDTO> getAllReaders() {
        return Mapper.convertToListUserDTO(userService.getAllAnons());
    }

    /**
     *
     * @return
     */
    @GetMapping("/traders")
    public List<UserDTO> getAllTraders() {
        return Mapper.convertToListUserDTO(userService.getAllTraders());
    }


    @PutMapping("/my")
    public void updateUser(@RequestBody @Valid UserDTO userDTO,
                           Principal principal) {
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        userService.updateUser(user, userDTO);
    }

    @PatchMapping("/{id}/change-role")
    public void changeRole(@PathVariable("id") int id,
                           @RequestBody @Valid RoleDTO roleDTO) {
        User user = userService.getUser(id);

        if (userService.isAdmin(user)) {
//            throw new NotEnoughRightException("You can't change role of admin!");
        }
        userService.changeRole(user, roleDTO.getRole());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id, Principal principal) {
        User user = userService.getUser(id);
        User principalUser = userService.getUserByEmailAndEnabled(principal.getName());

        if (!userService.isAdmin(principalUser) && !principalUser.equals(user)) {
//            throw new NotEnoughRightException("You can't delete this user!");
        }
        if (userService.isAdmin(user)) {
//            throw new NotEnoughRightException("You can't delete admin!");
        }
        userService.deleteUser(user);
    }
}
