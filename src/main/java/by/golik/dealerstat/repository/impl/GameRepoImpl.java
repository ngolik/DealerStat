package by.golik.dealerstat.repository.impl;

import by.golik.dealerstat.entity.Comment;
import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.repository.GameRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nikita Golik
 */
@Repository
@Transactional
public class GameRepoImpl implements GameRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentRepoImpl.class);

    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addGame(Game game) {
        getSession().persist(game);
        LOGGER.info("Game successfully added. Game details: " + game);
    }

    @Override
    public void updateGame(Game game) {
        getSession().update(game);
        LOGGER.info("Game successfully updated. Game details: " + game);
    }

    @Override
    public void removeGame(Integer id) {
        Game game = (Game) getSession().load(Game.class, id);

        if(game !=null){
            getSession().delete(game);
        }
        LOGGER.info("Game successfully deleted. Game details: " + game);
    }

    @Override
    public Game getGameById(Integer id) {
        Game game = (Game) getSession().load(Game.class, id);
        LOGGER.info("Game successfully loaded. Game details: " + game);
        return game;
    }

    @Override
    public List<Game> listGames() {
        List<Game> games = getSession().createQuery("from by.golik.dealerstat.entity.Game ORDER BY name ASC").list();
        for(Game game : games){
            LOGGER.info("Game list: " + game);
        }
        return games;
    }
}
