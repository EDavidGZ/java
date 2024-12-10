package com.edgz.authudemy.repos;

import com.edgz.authudemy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.tokens t WHERE u.id = :userId AND t.refreshToken = :refreshToken AND t.expiredAt > :expiredAt")
    Optional<User> findByIdAndTokensRefreshTokenAndTokensExpiredAtGreaterThan(
            @Param("userId") Long userId,
            @Param("refreshToken") String refreshToken,
            @Param("expiredAt") LocalDateTime expiredAt);

    @Query("""
        SELECT u FROM User u
        JOIN u.passwordRecoveries pr
        WHERE pr.token = :token
       """)
    Optional<User> findByPasswordRecoveriesToken(@Param("token") String token);

}

