package by.golik.dealerstat;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.repository.GameRepository;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.impl.GameServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Nikita Golik
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;


    private GameServiceImpl gameService;

    private Optional<Game> testGame;

    @Before
    public void init() {
        gameService = new GameServiceImpl(gameRepository);
        testGame = Optional.of(new Game("game"));
        when(gameRepository.findById((long) 1)).thenReturn(testGame);
    }

    @Test
    public void getGameTest() {
        Optional<Game> ts = gameService.findByGameId((long) 1);

        assertNotNull(ts);
        gameService.findByGameId((long)2);
    }

    @Test
    public void updateGameTest() {
        Game game = new Game("old_game");
        assertEquals(game.getName(), "old_game");
    }
}
