package moe.dyedmagenta.ecp.repository;

import moe.dyedmagenta.ecp.domain.Tasks;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Tasks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

    @Query("select tasks from Tasks tasks where tasks.user.login = ?#{principal.username}")
    List<Tasks> findByUserIsCurrentUser();

}
