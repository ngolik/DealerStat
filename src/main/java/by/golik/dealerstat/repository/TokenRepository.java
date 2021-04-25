package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findFirstByToken(String token);

    @Transactional
    @Modifying
    @Query("delete from Token t where t.expiryDate < current_timestamp")
    void deleteByExpiryDateBeforeCurrent();
}
