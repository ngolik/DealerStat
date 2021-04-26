package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.NewPasswordDTO;
import by.golik.dealerstat.service.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Nikita Golik
 */
public interface UserService extends UserDetailsService {
    void createUser(UserDTO userDTO);
    void createCode(String email);
    void confirmUser(String token);
    void confirmCode(NewPasswordDTO newPasswordDTO);
    User getUser(long id);
    User getUserByEmailAndEnabled(String email);
    User getByEmailAndPassword(String email, String password) throws Exception;
    List<User> getAllUsers();
    List<User> getAllAnons();
    List<User> getAllTraders();
    boolean isAdmin(User user);
    void updateUser(User user, UserDTO userDTO);
    void changeRole(User user, String roleName);
    void deleteUser(User user);
    void calculateRate(User user);
    List<User> getAllTradersByGames(List<Long> idList);
}
