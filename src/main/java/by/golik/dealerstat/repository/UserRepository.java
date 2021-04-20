package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Nikita Golik
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
