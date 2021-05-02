package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.NotEnoughRightException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.RoleDTO;
import by.golik.dealerstat.service.dto.UserDTO;
import by.golik.dealerstat.service.util.UserDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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

    private final GameObjectService gameObjectService;

    @Autowired
    public UserController(UserService userService, GameObjectService gameObjectService) {
        this.userService = userService;
        this.gameObjectService = gameObjectService;
    }

    /**+
     * Method to find user by his id
     * @param id - unique identifier of user
     * @param principal - the currently logged in user
     * @return
     * @throws ResourceNotFoundException -  if user doesn't exist
     */
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") int id, Principal principal) throws ResourceNotFoundException {
        User user = userService.getUser(id);
        User principalUser = userService.getUserByEmailAndEnabled(principal.getName());
        UserDTO userDTO;

        userService.calculateRate(user);
        userDTO = UserDtoAssembler.toDto(user);
        if (userService.isAdmin(principalUser) || principalUser.getId() == id) {
            userDTO.setEmail(user.getEmail());
        }
        return userDTO;
    }

    /**
     * Method to find all users
     * @return - list of users
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return UserDtoAssembler.toDtoList(userService.getAllUsers());
    }

    /**
     * Method to find all anonymous users
     * @return - list of anonymous users
     */
    @GetMapping("/readers")
    public List<UserDTO> getAllAnons() {
        return UserDtoAssembler.toDtoList(userService.getAllAnons());
    }

    /**
     * Method to see all traders
     * @param max - max value of rating
     * @param min - min value of rating
     * @param games - list of games
     * @return - list of value objects dto of User
     */
    @GetMapping("/traders")
    public List<UserDTO> getAllTraders(@RequestParam(required = false, defaultValue = "5")
                                       @DecimalMax(value = "5.0") double max,
                                       @RequestParam(required = false, defaultValue = "1") @DecimalMin(value = "1.0") double min,
                                       @RequestParam(required = false) String games) {
        String[] split;
        List<Integer> idList;
        List<User> users;

        if (min > max) {
            throw new ValidationException("min parameter should be less than max!");
        }
        try {
            split = games.split(",");
            idList = gameObjectService.getGameIdByName(split);
            if (idList == null) {
                return new ArrayList<UserDTO>();
            }
            users = userService.getAllTradersByGames(idList);
        }
        catch (NullPointerException e) {
            users = userService.getAllTraders();
            System.out.println(users);
        }
        return UserDtoAssembler.toDtoList(users);
    }

    /**
     * Method to update information of user
     * @param userDTO - value object dto of user
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException -  if user doesn't exist
     */
    @PutMapping("/my")
    public void updateUser(@RequestBody @Valid UserDTO userDTO,
                           Principal principal) throws ResourceNotFoundException {
        User user = userService.getUserByEmailAndEnabled(principal.getName());

        userService.updateUser(user, userDTO);
    }

    /**
     * Method to change role of user
     * @param id - unique identifier of user
     * @param roleDTO - value object of role
     * @throws ResourceNotFoundException -  if user doesn't exist
     * @throws NotEnoughRightException - if user doesn't have enough rights
     */
    @PatchMapping("/{id}/change-role")
    public void changeRole(@PathVariable("id") int id,
                           @RequestBody @Valid RoleDTO roleDTO) throws ResourceNotFoundException,
            NotEnoughRightException {
        User user = userService.getUser(id);

        if (userService.isAdmin(user)) {
            throw new NotEnoughRightException("You can't change role of admin!");
        }
        userService.changeRole(user, roleDTO.getRole());
    }

    /**
     * Method to delete user by his Id
     * @param id - unique identifier of user
     * @param principal - the currently logged in user
     * @throws ResourceNotFoundException -  if user doesn't exist
     * @throws NotEnoughRightException if user doesn't have enough rights
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id, Principal principal) throws ResourceNotFoundException,
            NotEnoughRightException {
        User user = userService.getUser(id);
        User principalUser = userService.getUserByEmailAndEnabled(principal.getName());

        if (!userService.isAdmin(principalUser) && !principalUser.equals(user)) {
            throw new NotEnoughRightException("You can't delete this user!");
        }
        if (userService.isAdmin(user)) {
            throw new NotEnoughRightException("You can't delete admin!");
        }
        userService.deleteUser(user);
    }
}
