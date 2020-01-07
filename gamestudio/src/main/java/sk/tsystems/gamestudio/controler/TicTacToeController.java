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
import sk.tsystems.gamestudio.game.ticTacToe.Field;
import sk.tsystems.gamestudio.game.ticTacToe.Tile;
import sk.tsystems.gamestudio.game.ticTacToe.Tile.State;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingServiceJPA;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/ticTacToe")
public class TicTacToeController {

	private Field field;
	private final String nameOfTheGame = "ticTacToe";
	private int valueOfLastTile = 0;
	public int getValueOfLastTile() {
		return valueOfLastTile;
	}

	public void setValueOfLastTile(int valueOfLastTile) {
		this.valueOfLastTile = valueOfLastTile;
	}

	Tile lastTile = new Tile();

	@Autowired
	private CommentService commentService;

	@Autowired
	private RatingServiceJPA ratingService;

	@Autowired
	private MainController mainController;

	@RequestMapping
	public String index() {

		field = new Field(19, 19);

		return nameOfTheGame;
	}

	@RequestMapping("/action")
	public String action(int row, int column) {
		if(!isSolved())
		field.openTile(row, column, field.getValueOfLastTile());
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

	private String getImage(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case X:
			return "x";
		case O:
			return "o";
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
				f.format("<a href='/ticTacToe/action?row=%d&column=%d'><img src='/tictactoe/%s.png'/></a> ", row,
						column, getImage(tile));
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

	public List<Comment> getComments() {
		return commentService.getComments(nameOfTheGame);
	}

	public String getRatings() {

		return ratingService.findRatingByGame(nameOfTheGame);
	}

}
