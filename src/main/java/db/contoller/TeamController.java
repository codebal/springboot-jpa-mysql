package db.contoller;

import db.entity.Team;
import db.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

	private final TeamService teamService;

	public TeamController(
		TeamService teamService
	) {
		this.teamService = teamService;
	}

	@GetMapping("/new")
	public Team saveRand(
		@RequestParam(name = "delay", defaultValue = "1000") Long delay
	) throws Throwable {
		Thread.sleep(delay);
		return teamService.saveRand();
	}

	@GetMapping("/last10")
	public List<Team> last10() {
		return teamService.getLast10();
	}

	@GetMapping("/get/{id}")
	public Team get(
		@PathVariable Long id
	) {
		return teamService.get(id);
	}
}
