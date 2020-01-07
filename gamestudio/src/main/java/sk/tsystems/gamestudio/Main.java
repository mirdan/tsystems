package sk.tsystems.gamestudio;

import java.util.List;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.PlayerServiceJPA;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceJDBC;

public class Main {

	public static void main(String[] args) {
		
		String user = System.getProperty("user.name");
		System.out.println(user);
		
		
//		long startMillis = System.currentTimeMillis();
//		long seconds = (System.currentTimeMillis() - startMillis) / 1000;

		
		
		ScoreService scoreService = new ScoreServiceJDBC();
//		RegisterServiceJPA registerService = new RegisterServiceJPA();
		
		//scoreService.addScore(new Score("masterOfTheWorld", "npuzzle" , 999));
	
		
		for (Score score : scoreService.getTopScores("npuzzle")) {
			System.out.println( " name: " + score.getUsername() +  " value: " + score.getValue());
		}
		

		
		
		
		
		

	}

}
