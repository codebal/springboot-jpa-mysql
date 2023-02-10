package db.entity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private int players;

	@Column(columnDefinition = "datetime(6) default current_timestamp(6) not null")
	private LocalDateTime createdAt;

	@Column(columnDefinition = "datetime(6)")
	private LocalDateTime updatedAt;

	public Team() {}

	public Team(String name) {
		this.name = name;
	}

//	@PrePersist
//	private void prePersist() {
//		this.createdAt = LocalDateTime.now();
//	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPlayers() {
		return players;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
