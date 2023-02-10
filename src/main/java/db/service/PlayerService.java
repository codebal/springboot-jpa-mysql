package db.service;

import db.entity.Player;
import db.jpa.PlayerJpaRepository;
import db.util.TransactionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class PlayerService {
	private final PlayerJpaRepository playerJpaRepository;
	private final TeamService teamService;

	public PlayerService(
		PlayerJpaRepository playerJpaRepository,
		TeamService teamService
	) {
		this.playerJpaRepository = playerJpaRepository;
		this.teamService = teamService;
	}

	public List<Player> getAll() {
		return playerJpaRepository.findAll();
	}

	public List<Player> getLast10() {
		return playerJpaRepository.findTop10ByOrderByIdDesc();
	}

	public Player get(Long id) {
		return playerJpaRepository.getOne(id);
	}

	public Player save(Player player) {
		return playerJpaRepository.save(player);
	}

	public Player saveRand(Long teamId) {
		//TransactionUtils.monitor(null);
		var rand = new Random();
		teamId = Objects.isNull(teamId) ? rand.nextLong() : teamId;
		var newPlayer = new Player(
			teamId,
			UUID.randomUUID().toString().substring(0,10),
			rand.nextInt(50)
		);
		var result = playerJpaRepository.save(newPlayer);
		return result;
	}

	@Transactional
	public Player saveRandTx(Long teamId) {
		try {
			return saveRand(teamId);
		} catch(Exception e) {
			return null;
		}
	}

	@Transactional(propagation = Propagation.NEVER)
	public Player saveRandTxNever(Long teamId) {
		return saveRand(teamId);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Player saveRandTxNotSupported(Long teamId) {
		return saveRand(teamId);
	}

	@Transactional(propagation = Propagation.NESTED)
	public Player saveRandTxNested(Long teamId) {
		return saveRand(teamId);
	}

	//@Transactional(propagation = Propagation.SUPPORTS)
	public Player saveRandOther(Long teamId) {
		TransactionUtils.monitor(null);
		//teamService.saveRandOther();
		return saveRand(teamId);
	}

	@Transactional
	public Player saveRandIntDBError(Long teamId) {
		var rand = new Random();
		teamId = Objects.isNull(teamId) ? rand.nextLong() : teamId;
		var newPlayer = new Player(
			teamId,
			UUID.randomUUID().toString().substring(0,10),
			-1
		);
		var result = playerJpaRepository.save(newPlayer);
		return result;
	}



	@Transactional
	public Player saveRandNormalError(Long teamId) {
		var result = saveRand(teamId);
		throw new RuntimeException("그냥 에러");
	}

	@Transactional
	public Player saveRandNormalErrorHandle(Long teamId) {
		try {
			var result = saveRand(teamId);
			throw new RuntimeException("그냥 에러");
		} catch(Exception e) {
			System.out.println("안에서 예외처리");
			return null;
		}
	}

	@Transactional
	public Player saveRandIntDBErrorHandle(Long teamId) {
		try {
			var rand = new Random();
			teamId = Objects.isNull(teamId) ? rand.nextLong() : teamId;
			var newPlayer = new Player(
				teamId,
				UUID.randomUUID().toString().substring(0,10),
				-1
			);
			var result = playerJpaRepository.save(newPlayer);
			return result;
		} catch(Exception e) {
			System.out.println("안에서 예외처리");
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Player saveRandNewTransaction(Long teamId) {
		TransactionUtils.monitor(null);
		return saveRand(teamId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Player saveRandNewTransactionError(Long teamId) {
		TransactionUtils.monitor(null);
		return saveRandIntDBError(teamId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Player saveRandNewTransactionErrorHandle(Long teamId) {
		TransactionUtils.monitor(null);
		return saveRandIntDBErrorHandle(teamId);
	}

	@Transactional
	public Player saveRandError(Long teamId) {
		TransactionUtils.monitor(null);
		var player = saveRand(teamId);
		throwError();
		return player;
	}

	public Player saveRandErrorHandled(Long teamId) {
		TransactionUtils.monitor(null);
		try {
			var player = saveRand(teamId);
			throwError();
			return player;
		} catch(RuntimeException e) {
			System.out.println("예외처리 - playerService");
		}
		return null;
	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean saveWithTeam(Long teamId) {
		IntStream.range(0, 1).forEach(
			i -> {
				saveRand(teamId);
			}
		);
		var team = teamService.get(teamId);

//		if (TransactionSynchronizationManager.isSynchronizationActive()) {
//			TransactionSynchronizationManager.registerSynchronization(
//				new TransactionSynchronization() {
//					@Override
//					public void afterCompletion(int status) {
//						System.out.println("saveWithTeam - afterCompletion");
//						System.out.println("상태 - " + status);
//						System.out.println(LocalDateTime.now());
//					}
//
//					@Override
//					public void afterCommit() {
//						System.out.println("saveWithTeam - afterCommit");
//						System.out.println(LocalDateTime.now());
//					}
//				}
//			);
//		}

		if (team == null) {
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			System.out.println("팀이 존재하지 않음");
			throw new RuntimeException("팀이 존재하지 않음");
		}

		return true;
	}

	public void throwError() {
		throw new RuntimeException("에러발생 - playerService");
	}
}
