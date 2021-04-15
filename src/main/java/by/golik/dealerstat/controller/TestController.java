package by.golik.dealerstat.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nikita Golik
 */
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "public API";
    }

    @GetMapping("/anon")
    @PreAuthorize("hasRole('ANONYMOUS') or hasRole('TRADER') or hasRole('ADMIN')")
    public String userAccess() {
        return "anon API";
    }

    @GetMapping("/trader")
    @PreAuthorize("hasRole('TRADER') or hasRole('ADMIN')")
    public String moderatorAccess() {
        return "trader API";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "admin API";
    }
}
