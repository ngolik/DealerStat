package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.Game;
import by.golik.dealerstat.entity.GameObject;
import by.golik.dealerstat.repository.GameObjectRepository;
import by.golik.dealerstat.service.GameObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
@Service
@Transactional
public class GameObjectServiceImpl implements GameObjectService {

    GameObjectRepository gameObjectRepository;

    @Autowired
    public void setGameObjectRepository(GameObjectRepository gameObjectRepository) {
        this.gameObjectRepository = gameObjectRepository;
    }

    @Override
    public Optional<GameObject> findByGameObjectId(Long id) {
        return gameObjectRepository.findById(id);
    }

    @Override
    public List<GameObject> findAllGameObjects() {
        return gameObjectRepository.findAll();
    }

    @Override
    public void saveGameObject(GameObject gameObject) {
        gameObjectRepository.save(gameObject);
    }

    @Override
    public void deleteGameObjectById(Long id) {
        gameObjectRepository.deleteById(id);
    }

    @Override
    public HttpStatus update(GameObject gameObject, Long id) {
        Optional<GameObject> gameObjectOptional = gameObjectRepository.findById(id);
        if (!gameObjectOptional.isPresent()) {
            return HttpStatus.NOT_FOUND;
        }
        gameObject.setId(id);
        gameObjectRepository.save(gameObject);
        return HttpStatus.OK;
    }

    @Override
    public List<GameObject> findAllByOwner_Id(Long id) {
        return gameObjectRepository.findAllByOwner_Id(id);
    }

    @Override
    public List<GameObject> findAllByGame_Id(Long id) {
        return gameObjectRepository.findAllByGame_Id(id);
    }

    @Override
    public List<GameObject> findAllByGame_Name(String name) {
        return gameObjectRepository.findAllByGame_Name(name);
    }
}
