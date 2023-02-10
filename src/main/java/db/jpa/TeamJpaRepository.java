package db.jpa;

import db.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {
	List<Team> findTop10ByOrderByIdDesc();
}
