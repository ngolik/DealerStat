package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Role;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.security.Principal;
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

    //todo
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> get(@PathVariable int id) throws ResourceNotFoundException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(id);
        User principalUser = userService.getUserByEmailAndEnabled(principal.getName());
        userService.calculateRate(user);

        if (userService.isAdmin(principalUser) || principalUser.getId() == id) {
            user.setEmail(user.getEmail());
        }
        if (!this.userService.findByUserId(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return generateListResponse(users);
    }

    /**
     * norm
     * @return
     */
    @GetMapping(value = "/readers",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllReaders() {
        List<User> users = this.userService.getAllAnons();
        return generateListResponse(users);
    }

    /**
     * norm
     * @return
     */
    @GetMapping("/traders")
    public ResponseEntity<List<User>> getAllTraders(@RequestParam(required = false, defaultValue = "5")
                                       @DecimalMax(value = "5.0",
                                               message = "min parameter should be less or equal than 5.0") double max,
                                       @RequestParam(required = false, defaultValue = "1") @DecimalMin(value = "1.0",
                                               message = "min parameter should be more or equal than 1.0") double min,
                                       @RequestParam(required = false) String games,
                                       @RequestParam(required = false, defaultValue = "0")
                                       @Min(value = 0, message = "skip parameter should be more or equal than 0") int skip,
                                       @RequestParam(required = false, defaultValue = "0")
                                       @Min(value = 0, message = "limit parameter should be more or equal than 0") int limit) {
        List<User> users = this.userService.getAllTraders();
        return generateListResponse(users);
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateGame(@RequestBody User user, @PathVariable int id) {
        return new ResponseEntity<>(userService.update(user, id));
    }

    @PatchMapping(value = "/{id}/change-role", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> changeRole(@PathVariable("id") int id,
                           @RequestBody @Valid Role role) throws ResourceNotFoundException {
        User user = userService.getUser(id);
        return new ResponseEntity<>(userService.changeRole(user, role.getName()));
    }

    /**
     * if not enable - don't find this user
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) throws ResourceNotFoundException {
        User user = userService.getUser(id);
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected ResponseEntity<List<User>> generateListResponse(List<User> users) {
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
