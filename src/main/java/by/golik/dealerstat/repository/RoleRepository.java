package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita Golik
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
