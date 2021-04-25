package by.golik.dealerstat.service.util;

import by.golik.dealerstat.entity.*;
import by.golik.dealerstat.service.dto.CommentDTO;
import by.golik.dealerstat.service.dto.GameDTO;
import by.golik.dealerstat.service.dto.GameObjectDTO;
import by.golik.dealerstat.service.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Golik
 */
public class Mapper {
    public static User convertToUser(UserDTO userDTO, Role role) {
        return new User(userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getPassword(), userDTO.getEmail(), role);
    }

    public static UserDTO convertToUserDTO(User user) {
        return new UserDTO().builder().id(user.getId()).firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(user.getRole().getName()).rate(user.getRate()).build();
    }

    public static List<UserDTO> convertToListUserDTO(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user: users) {
            userDTOS.add(Mapper.convertToUserDTO(user));
        }
        return userDTOS;
    }

    public static GameObject convertToGameObject(GameObjectDTO gameObjectDTO, boolean approved,
                                           User user, List<Game> games) {
        return new GameObject().builder().title(gameObjectDTO.getTitle())
                .text(gameObjectDTO.getText()).status(Status.valueOf(gameObjectDTO.getStatus()))
                .approved(approved).user(user).games(games).build();
    }

    public static UnconfirmedGameObject convertToUnconfirmedGameObject(GameObjectDTO gameObjectDTO,
                                                         List<Game> games, GameObject gameObject) {
        return new UnconfirmedGameObject(gameObjectDTO.getTitle(), gameObjectDTO.getText(),
                Status.valueOf(gameObjectDTO.getStatus()), games, gameObject);
    }

    public static GameObjectDTO convertToPostDTO(GameObject gameObject) {
        List<GameDTO> gameDTOS = convertToListGameDTO(gameObject.getGames());

        return new GameObjectDTO().builder().id(gameObject.getId())
                .title(gameObject.getTitle()).text(gameObject.getText())
                .status(gameObject.getStatus().name())
                .authorId(gameObject.getAuthor().getId())
                .createdAt(gameObject.getCreatedAt())
                .updatedAt(gameObject.getUpdatedAt())
                .games(gameDTOS).build();
    }

    public static List<GameDTO> convertToListGameDTO(List<Game> games) {
        List<GameDTO> gameDTOS = new ArrayList<>();

        games.forEach(game ->
                gameDTOS.add(new GameDTO(game.getId(), game.getName())));
        return gameDTOS;
    }

    public static List<GameObjectDTO> convertToListPostDTO(List<GameObject> gameObjects) {
        List<GameObjectDTO> postDTOS = new ArrayList<>();

        for (GameObject gameObject: gameObjects) {
            postDTOS.add(convertToPostDTO(gameObject));
        }
        return postDTOS;
    }

    public static Comment convertToComment(CommentDTO commentDTO, boolean approved,
                                           User user, GameObject gameObject) {
        return new Comment(commentDTO.getMessage(), commentDTO.getRate(),
                approved, gameObject, user);
    }

    public static CommentDTO convertToCommentDTO(Comment comment) {
        return new CommentDTO().builder().id(comment.getId())
                .message(comment.getMessage()).rate(comment.getRate())
                .createdAt(comment.getCreatedAt()).updatedAt(comment.getUpdatedAt())
                .authorId(comment.getAuthor().getId())
                .gameObjectId(comment.getGameobject().getId()).build();
    }

    public static List<CommentDTO> convertToListCommentDTO(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (Comment comment: comments) {
            commentDTOS.add(convertToCommentDTO(comment));
        }
        return commentDTOS;
    }

    public static UnconfirmedComment convertToUnconfirmedComment(CommentDTO commentDTO,
                                                               Comment comment) {
        return new UnconfirmedComment(commentDTO.getMessage(), commentDTO.getRate(),
                comment);
    }
}
