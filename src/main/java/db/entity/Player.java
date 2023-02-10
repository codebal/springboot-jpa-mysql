package db.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "player")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long teamId;

	private String name;

	private Integer age;

	public Player() {

	}

	public Player(Long teamId, String name, Integer age) {
		this.teamId = teamId;
		this.name = name;
		this.age = age;
	}

	@Column(columnDefinition = "datetime(6) default current_timestamp(6) not null")
	private LocalDateTime createdAt;

	@Column(columnDefinition = "datetime(6)")
	private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getTeamId() {
		return teamId;
	}

	public Integer getAge() {
		return age;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	//	@PrePersist
//	private void prePersist() {
//		this.createdAt = LocalDateTime.now();
//	}
}
