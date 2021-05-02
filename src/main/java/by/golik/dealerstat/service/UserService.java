package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.ResourceAlreadyExistException;
import by.golik.dealerstat.exception.ResourceNotFoundException;
import by.golik.dealerstat.service.dto.NewPasswordDTO;
import by.golik.dealerstat.service.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Interface with methods to action with {@link User} User Entity
 * @author Nikita Golik
 */
public interface UserService extends UserDetailsService {

    /**
     * This method allows to create User model based on DTO User
     * @param userDTO - value object of user
     * @throws ResourceAlreadyExistException - if user already exists
     */
    void createUser(UserDTO userDTO) throws ResourceAlreadyExistException;

    /**
     * This method creates code, that sends to user's email
     * @param email - email address of user
     * @throws ResourceNotFoundException - if token doesn't exist
     */
    void createCode(String email) throws ResourceNotFoundException;

    /**
     * This method allows to authorise user with his personal toker
     * @param token - verification token
     */
    void confirmUser(String token) throws ResourceNotFoundException;

    /**
     * This method allows to user confirm his identity
     * @param newPasswordDTO - new restored password
     * @throws ResourceNotFoundException - if user doesn't exist
     */
    void confirmCode(NewPasswordDTO newPasswordDTO) throws ResourceNotFoundException;

    /**
     * This method allows to find user by his id
     * @param id - unique identifier
     * @return - Entity model of user
     * @throws ResourceNotFoundException - if user not found
     */
    User getUser(int id) throws ResourceNotFoundException;

    /**
     * This method allows to find user by his email
     * @param email - email address of user
     * @return - Entity model of user
     * @throws ResourceNotFoundException - if user not found
     */
    User getUserByEmailAndEnabled(String email) throws ResourceNotFoundException;

    /**
     * This method allows to find user by his email and password
     * @param email - email address of user
     * @param password - passwrod from acoount
     * @return - Entity model of User
     * @throws ResourceNotFoundException - if user doesn't exist
     */
    User getByEmailAndPassword(String email, String password) throws ResourceNotFoundException;

    /**
     * This method allows to get all users
     * @return - list of users
     */
    List<User> getAllUsers();

    /**
     * This method allows to find all anonymous users
     * @return - list of anonymous users
     */
    List<User> getAllAnons();

    /**
     * This method allows to find all traders
     * @return - list of traders
     */
    List<User> getAllTraders();

    /**
     * This method checks user if he has admin root(ROLE_ADMIN)
     * @param user - user for checking
     * @return - boolean value, true if user is admin
     */
    boolean isAdmin(User user);

    /**
     * This method allows to update information about user
     * @param user - Entity model of user
     * @param userDTO - value object DTO of user
     */
    void updateUser(User user, UserDTO userDTO);

    /**
     * This method allows to change role of user
     * @param user - entity model of user
     * @param roleName - name of role
     */
    void changeRole(User user, String roleName);

    /**
     * This method allows to delete user from system
     * @param user - entity model of user
     */
    void deleteUser(User user);

    /**
     * This method allows to calculate rating of trader
     * @param user - trader
     */
    void calculateRate(User user);

    /**
     * This method allows to find all traders from games
     * @param idList - unique identifiers of games
     * @return - list of users
     */
    List<User> getAllTradersByGames(List<Integer> idList);

}
