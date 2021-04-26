package by.golik.dealerstat.repository;

import by.golik.dealerstat.entity.ReplyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Nikita Golik
 */
public interface ReplyCodeRepository extends JpaRepository<ReplyCode, Long> {
    Optional<ReplyCode> findByCode(String code);

    @Transactional
    @Modifying
    @Query("delete from ReplyCode c where c.expiryDate < current_timestamp")
    void deleteByExpiryDateBeforeCurrent();
}