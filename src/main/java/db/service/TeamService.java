package db.service;

import db.entity.Team;
import db.jpa.TeamJpaRepository;
import db.util.TransactionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
	private final TeamJpaRepository teamJpaRepository;

	public TeamService(
		TeamJpaRepository teamJpaRepository
	) {
		this.teamJpaRepository = teamJpaRepository;
	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Team saveRand() {
		var newTeam = new Team(
			UUID.randomUUID().toString().substring(0,10)
		);
		var result = teamJpaRepository.save(newTeam);

//		if (TransactionSynchronizationManager.isSynchronizationActive()) {
//			TransactionSynchronizationManager.registerSynchronization(
//				new TransactionSynchronization() {
//					@Override
//					public void afterCompletion(int status) {
//						System.out.println("teamSave - afterCompletion");
//						System.out.println("상태 - " + status);
//						System.out.println(LocalDateTime.now());
//						var newTeam = new Team(
//							UUID.randomUUID().toString().substring(10,20)
//						);
//						var result = teamJpaRepository.save(newTeam);
//					}
//
//					@Override
//					public void afterCommit() {
//						System.out.println("teamSave - afterCommit");
//						System.out.println(LocalDateTime.now());
//					}
//				}
//			);
//		}

		return result;
	}

	@Transactional
	public void saveRandOther() {
		TransactionUtils.monitor(null);
		saveRand();
		saveRand();
		saveRand();
	}

	public List<Team> getLast10() {
		return teamJpaRepository.findTop10ByOrderByIdDesc();
	}

	public Team get(Long id) {
		var daTeam = teamJpaRepository.findById(id).orElse(null);

		return daTeam;
	}

	public void throwError() {
		throw new RuntimeException("에러발생 - teamService");
	}

}
