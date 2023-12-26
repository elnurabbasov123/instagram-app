package app.repository;

import app.model.entity.Token;
import app.model.entity.User;
import app.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

   @Query("select t from _tokens t  " +
           "  where t.name =:token and t.tokenType =:tokenType and t.status = true " +
           "and t.expiredDate > CURRENT_TIMESTAMP ")
   @Transactional
   Optional<Token> getTokenByType(@Param("token") String token,@Param("tokenType") TokenType tokenType);

   @Modifying
   @Transactional(propagation = Propagation.REQUIRES_NEW)
   @Query("update _tokens t set t.status=false where t.tokenType =:tokenType and t.user=:user")
   void deleteAllTokenByType(@Param("user") User user, @Param("tokenType") TokenType tokenType);
}
