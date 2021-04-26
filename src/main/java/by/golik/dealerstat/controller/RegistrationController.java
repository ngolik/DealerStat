package by.golik.dealerstat.controller;

import by.golik.dealerstat.config.jwt.JwtProvider;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    public void confirmUser(@PathVariable("token") String token) {
        userService.confirmUser(token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public RegistrationResponse auth(@RequestBody @Valid RegistrationRequest request) throws Exception {
        User user = userService.getByEmailAndPassword(request.getLogin(),
                request.getPassword());
        String token = jwtProvider.generateToken(user.getEmail());

        return new RegistrationResponse(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserDTO userDTO) {
        userService.createUser(userDTO);
    }

    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCode(@RequestBody @Valid EmailDTO emailDTO) {
        userService.createCode(emailDTO.getEmail());
    }

    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public void confirmCode(@RequestBody @Valid NewPasswordDTO newPasswordDTO) {
        userService.confirmCode(newPasswordDTO);
    }
}
