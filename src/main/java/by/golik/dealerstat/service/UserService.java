package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface UserService extends UserDetailsService {
    public Optional<User> findByUserId(Long id);
    public List<User> findAllUsers();
    public void saveUser(User user);
    public void deleteUserById(Long id);
    HttpStatus update(User user, Long id);
}
