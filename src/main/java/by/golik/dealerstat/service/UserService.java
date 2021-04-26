package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.service.dto.NewPasswordDTO;
import by.golik.dealerstat.service.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

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
//    List<User> getAllTradersByGames(List<Integer> idList);
//    List<User> filterTraders(List<User> users, double max,
//                             double min, int skip, int limit);
    boolean isAdmin(User user);
    void updateUser(User user, UserDTO userDTO);
    void changeRole(User user, String roleName);
    void deleteUser(User user);
//    void calculateRating(User user);
}
