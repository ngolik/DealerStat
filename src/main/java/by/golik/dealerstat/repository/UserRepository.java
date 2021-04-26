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

    @Query("select u from User u where u.role.name = 'ROLE_ANON'")
    List<User> findAllAnons();

    @Query("select u from User u where u.role.name <> 'ROLE_ANON'")
    List<User> findAllNonReaders();

    @Transactional
    void deleteByTokenIsNullAndEnabledFalse();
}
