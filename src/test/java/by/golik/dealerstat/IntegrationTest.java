package by.golik.dealerstat;

import by.golik.dealerstat.controller.UserController;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.entity.Role;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.exception.NotEnoughRightException;
import by.golik.dealerstat.repository.*;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.UserDTO;
import by.golik.dealerstat.service.impl.GameObjectServiceImpl;
import by.golik.dealerstat.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Nikita Golik
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader= AnnotationConfigContextLoader.class)
public class IntegrationTest {

    @Mock
    private static UserRepository userRepository;

    @Mock
    private static RoleRepository roleRepository;

    @Mock
    private static TokenRepository tokenRepository;

    @Mock
    private static ReplyCodeRepository replyCodeRepository;

    @Mock
    private static GameObjectRepository gameObjectRepository;

    @Mock
    private static UnconfirmedGameObjectRepository unconfirmedGameObjectRepository;

    @Mock
    private static GameRepository gameRepository;

    @Mock
    private Principal principalTrader;

    private List<User> testTraders;

    private Optional<User> testTrader;

    private Optional<User> testReader;

    @Configuration
    static class ContextConfiguration {

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserService userService() {
            UserService userService = new UserServiceImpl(userRepository, roleRepository,
                    tokenRepository, replyCodeRepository, bCryptPasswordEncoder());
            return userService;
        }

        @Bean
        public GameObjectService gameObjectService() {
           GameObjectService gameObjectService = new GameObjectServiceImpl(gameObjectRepository,
                    unconfirmedGameObjectRepository, gameRepository);
           return gameObjectService;
        }

        @Bean
        public UserController userController() {
            UserController userController = new UserController(userService(),
                    gameObjectService());
            return userController;
        }
    }

    @Autowired
    private UserController userController;

    @Before
    public void init() {
        User trader1 = new User("First","First","First",
                "first@gmail.com", new Role("ROLE_TRADER"));
        User trader2 = new User("Second","Second","Second",
                "second@gmail.com", new Role("ROLE_TRADER"));
        User trader3 = new User("Third","Third","Third",
                "third@gmail.com", new Role("ROLE_TRADER"));
        User trader4 = new User("Fourth","Fourth","Fourth",
                "fourth@gmail.com", new Role("ROLE_TRADER"));
        User trader5 = new User("Fifth","Fifth","Fifth",
                "fifth@gmail.com", new Role("ROLE_TRADER"));

        testTraders = new ArrayList<>();
        testTraders.add(trader1);
        testTraders.add(trader2);
        testTraders.add(trader3);
        testTraders.add(trader4);
        testTraders.add(trader5);
        testTrader = Optional.of(new User("Trader","Trader",
                "Trader","trader@gmail.com",
                new Role("ROLE_TRADER")));
        testReader = Optional.of(new User("Reader","Reader",
                "Reader","reader@gmail.com",
                new Role("ROLE_READER")));
        when(principalTrader.getName()).thenReturn("Trader");
        when(userRepository.findByEmailAndEnabledTrue("Trader")).thenReturn(testTrader);
        when(userRepository.findByIdAndEnabledTrue(1)).thenReturn(testTrader);
        when(userRepository.findByIdAndEnabledTrue(2)).thenReturn(testReader);
        when(userRepository.findAllNonReaders()).thenReturn(testTraders);
        when(userRepository.findRateByUser(testTraders.get(0))).thenReturn(3.1);
        when(userRepository.findRateByUser(testTraders.get(1))).thenReturn(2.9);
        when(userRepository.findRateByUser(testTraders.get(2))).thenReturn(3.8);
        when(userRepository.findRateByUser(testTraders.get(3))).thenReturn(4.4);
        when(userRepository.findRateByUser(testTraders.get(4))).thenReturn(4.1);
    }

//    @Test
//    public void getAllTradersTest() {
//        List<UserDTO> results;
//
//        results = userController.getAllTraders(4.5, 3.0, null, 1, 2);
//        assertEquals(results.size(), 2);
//    }

//    @Test(expected = NotEnoughRightException.class)
//    public void deleteUserTest() {
//        userController.deleteUser(1, principalTrader);
//        userController.deleteUser(2, principalTrader);
//    }
}
