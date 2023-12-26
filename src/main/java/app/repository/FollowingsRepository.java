package app.repository;

import app.model.entity.Followings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingsRepository extends JpaRepository<Followings,Long> {
}
