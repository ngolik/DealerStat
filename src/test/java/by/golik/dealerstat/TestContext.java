package by.golik.dealerstat;

import by.golik.dealerstat.controller.UserController;
import by.golik.dealerstat.repository.*;
import by.golik.dealerstat.service.GameObjectService;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.impl.GameObjectServiceImpl;
import by.golik.dealerstat.service.impl.UserServiceImpl;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Nikita Golik
 */
@ContextConfiguration
public class TestContext {
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

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository,
                roleRepository, tokenRepository, replyCodeRepository,
                bCryptPasswordEncoder());
    }

    @Bean
    public GameObjectService gameObjectService() {
        return new GameObjectServiceImpl(gameObjectRepository,
                unconfirmedGameObjectRepository, gameRepository);
    }

    @Bean
    public UserController userController() {
        return new UserController(userService(),
                gameObjectService());
    }
}
