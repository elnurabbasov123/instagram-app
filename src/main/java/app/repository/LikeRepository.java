package app.repository;

import app.model.entity.Like;
import app.model.entity.Post;
import app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Transactional
    @Query("select l from _likes l where l.user=:user and l.post =:post")
    Optional<Like> findLikeByUser(@Param("user") User user,
                            @Param("post") Post post);
}
