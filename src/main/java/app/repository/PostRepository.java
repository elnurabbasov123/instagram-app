package app.repository;

import app.model.entity.Post;
import app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Override
    @Transactional
    <S extends Post> S save(S entity);

    @Transactional
    @Query("select p from _posts p" +
            " left join _followers f on f.user=p.user" +
            " where  p.user.userSituation.status =true " +
            "and p.user.userSituation.isBlocked=false " +
            "and p.user.userSituation.isPrivate=false " +
            "or p.user.userSituation.isPrivate= true " +
            "and p.user.userSituation.isBlocked=false " +
            "and p.user.userSituation.status=true " +
            "and f.follower =:user")
    List<Post> getAll(@Param("user")User user);

    @Query("select p from _posts p" +
            " left join _followers f on f.user=p.user" +
            " where  p.user.userSituation.status =true " +
            "and p.user.userSituation.isBlocked=false " +
            "and p.user.userSituation.isPrivate=false " +
            "and p.id =:id " +
            "or p.user.userSituation.isPrivate = true " +
            "and p.user.userSituation.isBlocked=false " +
            "and p.user.userSituation.status=true " +
            "and f.follower =:user and" +
            " p.id =:id ")
    Optional<Post> findById(@Param("user")User user,
                            @Param("id") Long id);
}

















