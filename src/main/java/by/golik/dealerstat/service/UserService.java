package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
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
    void createUser(UserDTO userDTO) throws ResourceAlreadyExistException;
    void createCode(String email) throws ResourceNotFoundException;
    void confirmUser(String token);
    void confirmCode(NewPasswordDTO newPasswordDTO);
    User getUser(long id) throws ResourceNotFoundException;
    User getUserByEmailAndEnabled(String email) throws ResourceNotFoundException;
    User getByEmailAndPassword(String email, String password) throws Exception, ResourceNotFoundException;
    List<User> getAllUsers();
    List<User> getAllAnons();
    List<User> getAllTraders();
    boolean isAdmin(User user);
    void updateUser(User user, UserDTO userDTO);
    HttpStatus changeRole(User user, String roleName);
    void deleteUser(User user);
    void calculateRate(User user);
    List<User> getAllTradersByGames(List<Long> idList);
    public Optional<User> findByUserId(Long id);
    HttpStatus update(User user, Long id);
}
