package sk.tsystems.gamestudio.controler;

import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingServiceJPA;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/minesweeper")
public class MinesweeperController {
	private Field field;
	private boolean marking;
	private final String nameOfTheGame = "minesweeper";
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RatingServiceJPA ratingService;
	
	@Autowired
	private ScoreService scoreService;

	@Autowired
	private MainController mainController;

	public boolean isMarking() {
		return marking;
	}

	@RequestMapping
	public String index() {
		field = new Field(9, 9, 10);
		return nameOfTheGame;
	}

	@RequestMapping("/action")
	public String action(int row, int column) {
		if (field.getState() == GameState.PLAYING)
			if (marking)
				field.markTile(row, column);
			else {
				field.openTile(row, column);
				if(field.isSolved() && mainController.isLogged()) {
					scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), nameOfTheGame, field.getScore()));
					
				}
			}
		return nameOfTheGame;
	}
	
	@RequestMapping("/comment")
	public String comment(String content) {
		if (mainController.isLogged()) {
			commentService.addComment(new Comment(mainController.getLoggedPlayer().getName(), nameOfTheGame, content));
		}
		return nameOfTheGame;
	}
	

	@RequestMapping("/change")
	public String change() {
		marking = !marking;
		return nameOfTheGame;
	}

	@RequestMapping("/rating")
	public String rating(int value) {
		if (mainController.isLogged()) {
			String playerName = mainController.getLoggedPlayer().getName();
			Rating rating = ratingService.findUserByNameAndGame(playerName, nameOfTheGame);
			if (rating == null) {
				ratingService.addRating(new Rating(playerName, nameOfTheGame, value));
			}else {
				ratingService.updateUsersRating(playerName, nameOfTheGame, value);
			}
		}
		return nameOfTheGame;
	}
	
	
	
	
	
	
	private String getImage(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case MARKED:
			return "marked";
		case OPEN:
			if (tile instanceof Clue)
				return "open" + ((Clue) tile).getValue();
			else
				return "mine";
		default:
			throw new IllegalArgumentException();

		}

	}

	public String getHtmlField() {
		Formatter f = new Formatter();
		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, column);
				f.format("<a href='/minesweeper/action?row=%d&column=%d'><img src='/mines/%s.png'/></a> ", row, column,
						getImage(tile));
				f.format("</td>\n");
			}
			f.format("</tr>\n");
		}
		f.format("</table>\n");
		return f.toString();
	}

	public boolean isSolved() {
		return field.isSolved();
	}
	
	public List<Score> getScores() {
		return scoreService.getTopScores(nameOfTheGame);
	}
	
	
	public List<Comment> getComments() {
		return commentService.getComments(nameOfTheGame);
	}
	
	
	public String getRatings() {

		return ratingService.findRatingByGame(nameOfTheGame);
	}
	
	public String getMessage() {
		return "Hello from java";
	}

}
