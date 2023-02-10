package db.contoller;

import db.entity.Player;
import db.service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

	private final PlayerService playerService;

	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping("/all")
	public List<Player> getAll() {
		return playerService.getAll();
	}

	@GetMapping("/last10")
	public List<Player> last10() {
		return playerService.getLast10();
	}

	@GetMapping("/new")
	public Player saveRand() {
		return playerService.saveRand(null);
	}

	@GetMapping("/test-save-with-team/{teamId}")
	public Object testSaveWithTeam(
		@PathVariable Long teamId
	) {
		return playerService.saveWithTeam(teamId);
	}
}
