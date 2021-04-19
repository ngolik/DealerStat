package by.golik.dealerstat.service;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface UserService {
    public Optional<User> findByUserId(Long id);
    public List<User> findAllUsers();
    public void saveUser(User user);
    public void deleteUserById(Long id);
}
