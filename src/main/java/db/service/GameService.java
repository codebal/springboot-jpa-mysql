package db.service;

import db.entity.Player;
import db.entity.Team;
import db.util.TransactionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class GameService {
	private final PlayerService playerService;
	private final TeamService teamService;

	@PersistenceContext
	EntityManager entityManager;

	public GameService(
		PlayerService playerService,
		TeamService teamService
	) {
		this.playerService = playerService;
		this.teamService = teamService;
	}

	@Transactional
	public List<Player> game1() {
		TransactionUtils.monitor(null);
		teamService.saveRand();
		playerService.saveRand(1L);
		try {
			playerService.saveRandIntDBError(1L);
		} catch(Exception e) {
			System.out.println("예외처리");
		}
		return playerService.getLast10();
	}

	@Transactional
	public List<Player> game2() {
		TransactionUtils.monitor(null);
		teamService.saveRand();
		playerService.saveRand(1L);
		playerService.saveRandIntDBErrorHandle(5L);
		playerService.saveRand(1L);
		playerService.saveRand(1L);
		return playerService.getLast10();
	}

	@Transactional
	public List<Player> game3() {
		TransactionUtils.monitor(null);
		teamService.saveRand();
		playerService.saveRand(1L);
		playerService.saveRandNewTransaction(5L);
		playerService.saveRand(1L);
		playerService.saveRand(1L);
		return playerService.getLast10();
	}

	@Transactional
	public List<Player> game4() {
		TransactionUtils.monitor(null);
		teamService.saveRand();
		playerService.saveRand(1L);
		try {
			playerService.saveRandNewTransactionError(5L);
		} catch(Exception e) {
			System.out.println("부모에서 예외처리");
		}
		playerService.saveRand(1L);
		playerService.saveRand(1L);
		return playerService.getLast10();
	}

	@Transactional
	public Object game5() {
		TransactionUtils.monitor(null);
		playerService.saveRand(1L);
		//playerService.saveRandOther(2L);
		//throwError();
		//var list = playerService.getLast10();
		System.out.println("game5 - 완료시간 : " + LocalDateTime.now());
		return "game5";
	}

	public void test1() {
		var start = LocalDateTime.now();
		IntStream.range(0, 10000)
			.forEach(i -> {
				playerService.saveRand(Long.valueOf(i));
				System.out.println("insert - " + (i));
			});
		var end = LocalDateTime.now();
		var diff = Timestamp.valueOf(end).getTime() - Timestamp.valueOf(start).getTime();
		System.out.println("diff - " + diff);
	}

	public void test2() {
		var start = LocalDateTime.now();
		IntStream.range(0, 10000)
			.forEach(i -> {
				playerService.saveRandTx(Long.valueOf(i));
				System.out.println("insert - " + (i));
			});
		var end = LocalDateTime.now();
		var diff = Timestamp.valueOf(end).getTime() - Timestamp.valueOf(start).getTime();
		System.out.println("diff - " + diff);
	}

	public void test3() {
		var start = LocalDateTime.now();
		IntStream.range(0, 10000)
			.forEach(i -> {
				playerService.saveRandTxNotSupported(Long.valueOf(i));
				System.out.println("insert - " + (i));
			});
		var end = LocalDateTime.now();
		var diff = Timestamp.valueOf(end).getTime() - Timestamp.valueOf(start).getTime();
		System.out.println("diff - " + diff);
	}


	@Transactional
	public void test4() {
		var start = LocalDateTime.now();
		IntStream.range(0, 10000)
			.forEach(i -> {
				playerService.saveRand(Long.valueOf(i));
				System.out.println("insert - " + (i));
			});
		var end = LocalDateTime.now();
		var diff = Timestamp.valueOf(end).getTime() - Timestamp.valueOf(start).getTime();
		System.out.println("diff - " + diff);
	}


	@Transactional
	public void test5() {
		playerService.saveRandTx(1L);
		try {
			playerService.saveRandNormalError(2L);
		}
		catch(Exception e) {
			System.out.println("예외처리");
		}
		playerService.saveRandTx(3L);
	}

	@Transactional
	public void test6() {
		var player1 = playerService.get(1L);
		player1.setAge(2000);

		var playerInsert = playerService.saveRand(10L);
		playerInsert.setName("새로운 플레이어 이름");

		var playerNew = new Player(2L, "new player", 20);
		entityManager.persist(playerNew);

		List<Player> list = playerService.getLast10();
		list.get(0).setName("처음꺼");
		list.get(1).setName("두번째꺼");
	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void testInsertTime() {
		//System.out.println("insert 전 : " + LocalDateTime.now());
		teamService.saveRand();
		//System.out.println("insert 후 : " + LocalDateTime.now());

		TransactionUtils.monitor(null);

		playerService.saveWithTeam(1L);
		//playerService.saveWithTeam(5L);
		//System.out.println("지금시간 : " + LocalDateTime.now());
	}

	void sleep(Long mils) {
		try {
			Thread.sleep(mils);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// @Transactional(isolation = Isolation.SERIALIZABLE)
	public List<Team> testSelect() {
		sleep(10000L);
		var list = teamService.getLast10();

		return list;
	}

	void printNow() {
		var thread = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			System.out.println("비동기 지금시간 : " + LocalDateTime.now());
		});
		thread.start();
	}

	public void throwError() {
		throw new RuntimeException("에러발생 - gameService");
	}

}
