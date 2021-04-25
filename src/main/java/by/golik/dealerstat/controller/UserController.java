package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nikita Golik
 */
//@RestController
//@RequestMapping("/traders")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> get(@PathVariable Long id) {
//        if (!this.userService.findByUserId(id).isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        User user = this.userService.findByUserId(id).get();
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<User>> getAll() {
//        List<User> users = this.userService.findAllUsers();
//        return generateListResponse(users);
//    }
//
//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> post(@RequestBody User user) {
//        HttpHeaders headers = new HttpHeaders();
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        this.userService.saveUser(user);
//        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
//    }
//
//    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> put(@RequestBody User user, @PathVariable Long id) {
//        return new ResponseEntity<>(userService.update(user, id));
//    }
//
//    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> delete(@PathVariable Long id) {
//        userService.deleteUserById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    protected ResponseEntity<List<User>> generateListResponse(List<User> users) {
//        if (users.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//}
