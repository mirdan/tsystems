package sk.tsystems.gamestudio.controler;

import java.util.Formatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.npuzzle.core.Field;
import sk.tsystems.gamestudio.game.npuzzle.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingServiceJPA;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/puzzle")
public class PuzzleController {
	private Field field;
	private final String nameOfTheGame = "puzzle";

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private RatingServiceJPA ratingService;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {
		field = new Field(4, 4);
		return "puzzle";
	}

	@RequestMapping("/move")
	public String move(int tile) {
		field.move(tile);
		if (field.isSolved() && mainController.isLogged()) {
			scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), nameOfTheGame, field.getScore()));
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

	public String getHtmlField() {
		Formatter f = new Formatter();
		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, column);
				if (tile != null) {
					f.format("<a href='/puzzle/move?tile=%d'>  <img src='/puzzle/img%d.jpg' alt=\"tile\"/>  </a>",
							tile.getValue(), tile.getValue());
				}
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

		return (String) ratingService.findRatingByGame(nameOfTheGame);
	}
	
	public String getMessage() {
		return "Hello from java";
	}

}
