package sk.tsystems.gamestudio.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.PlayerServiceJPA;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MainController {
	private Player loggedPlayer;
	private boolean validName;
	private boolean validPasswd;

	@Autowired
	private PlayerServiceJPA playerService;

	@RequestMapping("/")
	public String index() {
		validName = true;
		validPasswd = true;
		return "index";
	}

	@RequestMapping("/signin")
	public String signin(Player player) {
		
		String name = null;
		name = player.getName();
		validName = (name.length() > 2); 
		String password = player.getPasswd();
		validPasswd =  (name.length() > 2);

		if (name != null && !name.trim().equals("") && password.length() > 2 && name.length() > 2
				&& playerService.findUserByName(name) == null) {
			Player p = new Player(player.getName(), player.getPasswd());
			playerService.addUser(p);
			loggedPlayer = player;
		}
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Player player) {
		String name = null;
		name = player.getName();
		String password = player.getPasswd();
		if (name != null && !name.trim().equals("")) {
			Player userInDB = playerService.findUserByName(name);
			if (name != null && userInDB != null && password.equals(userInDB.getPasswd())) {
				loggedPlayer = player;
			}
		}
		return "index";
	}

	@RequestMapping("/logout")
	public String logout(Player player) {
		loggedPlayer = null;
		return "redirect:/";
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}
	
	public boolean isValidName() {
		return validName;
	}
	
	public boolean isValidPasswd() {
		return validPasswd;
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}

	public List<Player> listAllUsers() {
		return playerService.listAllUsers();
	}

	public String getMessage() {
		return "At LEAST 3 characters!!!";
	}

}
