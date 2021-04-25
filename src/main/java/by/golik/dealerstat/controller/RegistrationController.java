package by.golik.dealerstat.controller;

import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Nikita Golik
 */
//@RestController
//public class RegistrationController {
//
//    private UserServiceImpl userService;
//
//    @Autowired
//    public void setUserService(UserServiceImpl userService) {
//        this.userService = userService;
//    }
//
//
//
//
//    @GetMapping("/registration")
//    public String registration() {
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
//        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
//            model.addAttribute("passwordError", "Passwords are different!");
//        }
//
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
//
//            model.mergeAttributes(errors);
//
//            return "registration";
//        }
//
//        if (!userService.addUser(user)) {
//            model.addAttribute("usernameError", "User exists!");
//            return "registration";
//        }
//
//        return "redirect:/login";
//    }
//
//    @GetMapping("/activate/{code}")
//    public String activate(Model model, @PathVariable String code) {
//        boolean isActivated = userService.activateUser(code);
//
//        if (isActivated) {
//            model.addAttribute("message", "User successfully activated");
//        } else {
//            model.addAttribute("message", "Activation code is not found!");
//        }
//
//        return "login";
//    }
//}
