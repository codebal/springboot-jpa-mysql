package db.contoller;

import db.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameContoller {

	private final GameService gameService;

	public GameContoller(
		GameService gameService
	) {
		this.gameService = gameService;
	}

	@GetMapping("/1")
	public Object game1() {
		gameService.test1();
		return "game1";
	}

	@GetMapping("/2")
	public Object game2() {
		gameService.test2();
		return "game2";
	}

	@GetMapping("/3")
	public Object game3() {
		gameService.test3();
		return "game3";
	}

	@GetMapping("/4")
	public Object game4() {
		gameService.test4();
		return "game4";
	}

	@GetMapping("/5")
	public Object game5() {
		gameService.test6();
		return "game5";
	}

	@GetMapping("/test-insert-time")
	public Object testInsertTime() {
		gameService.testInsertTime();
		return "test";
	}

	@GetMapping("/test-select")
	public Object testSelect() {
		return gameService.testSelect();
	}

}
