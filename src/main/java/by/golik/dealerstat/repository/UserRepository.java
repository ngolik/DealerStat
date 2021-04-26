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

    Optional<User> findByEmailAndEnabledTrue(String email);

    Optional<User> findByIdAndEnabledTrue(long id);

//    @Query("select avg(c.rate) from User u, GameObject p, Comment c " +
//            "where u = :user and p.author = u and c.gameobject = p group by u")
//    Double findRateByUser(@Param("user") User user);

    @Query("select u from User u where u.role.name = 'ROLE_ANON'")
    List<User> findAllAnons();

    @Query("select u from User u where u.role.name <> 'ROLE_ANON'")
    List<User> findAllNonReaders();

    @Transactional
    void deleteByTokenIsNullAndEnabledFalse();

    @Query(nativeQuery = true, value = "select * from mydb.user u " +
            "where u.id in(select distinct g.author_id from mydb.gameobject g where g.id \n" +
            "in(select distinct k.gameobjects_id  from mydb.gameobject_game k " +
            " where k.game_id in(:games)))")
    List<User> findUserByGames(@Param("games") List<Integer> games);
}
