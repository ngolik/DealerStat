package by.golik.dealerstat.controller;

import by.golik.dealerstat.config.jwt.JwtProvider;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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

    @GetMapping("/confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    public void confirmUser(@PathVariable("token") String token) throws ResourceNotFoundException {
        userService.confirmUser(token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public RegistrationResponse auth(@RequestBody @Valid RegistrationRequest request) throws Exception, ResourceNotFoundException {
        User user = userService.getByEmailAndPassword(request.getLogin(),
                request.getPassword());
        String token = jwtProvider.generateToken(user.getEmail());

        return new RegistrationResponse(token);
    }

    /**
     * Good
     * @param userDTO
     * @throws ResourceAlreadyExistException
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserDTO userDTO) throws ResourceAlreadyExistException {
        userService.createUser(userDTO);
    }

    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCode(@RequestBody @Valid User user) throws ResourceNotFoundException {
        userService.createCode(user.getEmail());
    }

    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public void confirmCode(@RequestBody @Valid NewPasswordDTO newPasswordDTO) throws ResourceNotFoundException {
        userService.confirmCode(newPasswordDTO);
    }
}
