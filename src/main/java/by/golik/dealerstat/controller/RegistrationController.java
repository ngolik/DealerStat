package by.golik.dealerstat.controller;

import by.golik.dealerstat.config.jwt.JwtProvider;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.NewPasswordDTO;
import by.golik.dealerstat.service.dto.RegistrationRequest;
import by.golik.dealerstat.service.dto.RegistrationResponse;
import by.golik.dealerstat.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Nikita Golik
 */
@RestController
@RequestMapping("/auth")
public class RegistrationController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public RegistrationController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Method for confirming user with his token
     * @param token - verification token
     * @throws ResourceNotFoundException - if token doesn't exist
     */
    @GetMapping("/confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    public void confirmUser(@PathVariable("token") String token) throws ResourceNotFoundException {
        userService.confirmUser(token);
    }

    /**
     * Method for authorization in system by login and password
     * @param request - from client
     * @return - response
     * @throws ResourceNotFoundException - if user doesn't exist
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public RegistrationResponse auth(@RequestBody @Valid RegistrationRequest request) throws ResourceNotFoundException {
        User user = userService.getByEmailAndPassword(request.getLogin(),
                request.getPassword());
        String token = jwtProvider.generateToken(user.getEmail());

        return new RegistrationResponse(token);
    }

    /**
     * Method for register user ib system
     * @param userDTO - value object dto of user
     * @throws ResourceAlreadyExistException -  if user already exist
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserDTO userDTO) throws ResourceAlreadyExistException {
        userService.createUser(userDTO);
    }

    /**
     * Method for restoring password
     * @param user - entity model of User
     * @throws ResourceNotFoundException -  if user doesn't exist
     */
    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCode(@RequestBody @Valid User user) throws ResourceNotFoundException {
        userService.createCode(user.getEmail());
    }

    /**
     * Method for confirming restoring password
     * @param newPasswordDTO - new password after restoring
     * @throws ResourceNotFoundException -  if user doesn't exist
     */
    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public void confirmCode(@RequestBody @Valid NewPasswordDTO newPasswordDTO) throws ResourceNotFoundException {
        userService.confirmCode(newPasswordDTO);
    }
}
