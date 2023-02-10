package db.jpa;

import db.entity.Player;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerJpaRepository extends JpaRepository<Player, Long> {
	List<Player> findTop10ByOrderByIdDesc();
}
