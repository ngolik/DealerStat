package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Repository for Tokens
 * @author Nikita Golik
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findFirstByToken(String token);

    @Transactional
    @Modifying
    @Query("delete from Token t where t.expiryDate < current_timestamp")
    void deleteByExpiryDateBeforeCurrent();
}
