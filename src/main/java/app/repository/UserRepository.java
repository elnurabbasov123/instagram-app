package app.repository;

import app.model.entity.Followings;
import app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from _users u join _user_situation us on u.userSituation = us where u.id =:id and us.isBlocked = false and us.status = true")
    Optional<User> getByIdUs(@Param("id") Long id);
    @Query("select u from _users u join _user_situation us on u.userSituation = us where u.username =:username and us.isBlocked = false and us.status = true")
    Optional<User> getByUsername(@Param("username") String username);

    @Query("select f from _followings f where f.user =:user and f.following =:followingUser")
    Optional<Followings> checkFollowing(@Param("user")User user,
                                        @Param("followingUser")User followingUser);
}
