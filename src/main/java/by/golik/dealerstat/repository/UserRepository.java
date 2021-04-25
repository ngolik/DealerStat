package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Role;
import by.golik.dealerstat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByRoleAndEnabledTrue(Role role);

    boolean existsByEmail(String email);

//    @Query("select avg(c.rate) from User u, GameObject p, Comment c " +
//            "where u = :user and p.author = u and c.gameObject = p group by u")
//    Double findRateByUser(@Param("user") User user);

    Optional<User> findByEmailAndEnabledTrue(String email);

    Optional<User> findByIdAndEnabledTrue(long id);

    @Query("select u from User u where u.role.name = 'ROLE_ANON'")
    List<User> findAllAnons();

    @Query("select u from User u where u.role.name <> 'ROLE_ANON'")
    List<User> findAllNonReaders();


//    @Query("select u.firstName from  User where u.id" +
//            "in(select distinct p.author_id from gameobject p where p.id \n" +
//            "in(select distinct pg.gameobjects_id from gameobject_games pg" +
//            "where pg.game_id in(:games)))")
//    List<User> findUserByGames(@Param("games") List<Integer> games);

    @Transactional
    void deleteByTokenIsNullAndEnabledFalse();
}
