package sk.tsystems.gamestudio.game.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.gamestudio.game.minesweeper.UserInterface;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;




/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Starts the game.
	 * 
	 * @param field field of mines and clues
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();

			if (field.getState() == GameState.FAILED) {
				System.out.println("You FAILED ");
				System.exit(0);
			} else if (field.getState() == GameState.SOLVED) {
				System.out.println("Congratulation");
				System.exit(0);
			}

			processInput();

			// throw new UnsupportedOperationException("Resolve the game state - winning or
			// loosing condition.");
		} while (true);
	}

	/**
	 * Updates user interface - prints the field.
	 */
	@Override
	public void update() {
		System.out.print("  ");
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.printf("%d ", column);
		}
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.println("");
			System.out.printf("%c ", 'A' + row);
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile.getState() == State.OPEN) {
					if (tile instanceof Mine) {
						System.out.printf("%c ", 'X');
					} else if (tile instanceof Clue) {
						// Clue clue = (Clue) tile;
						// System.out.printf("%d", clue.getValue());
						// To iste ako vyssie akurat pretypovanie aj printf v jednom riadku
						System.out.printf("%d ", ((Clue) tile).getValue());
					}
				} else if (tile.getState() == State.MARKED) {
					System.out.printf("%c ", 'M');
				} else if (tile.getState() == State.CLOSED) {
					System.out.printf("%c ", '-');
				}
			}
		}
		System.out.println();
		System.out.println("Mines left:  " + field.getRemainingMineCount());
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		System.out.println("Please enter your selection <X>Exit, <MA1>MARK, <OB4>OPEN: ");
		String usersInput = readLine().toUpperCase();

		try {
			handleInput(usersInput);
		} catch (WrongFormatException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private void handleInput(String input) throws WrongFormatException {

		Pattern pattern = Pattern.compile("(O|M)([A-I])([0-8])", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			char operation = matcher.group(1).charAt(0);
			char rowAsChar = matcher.group(2).charAt(0);
			int row = rowAsChar - 'A';
			int column = Integer.parseInt(matcher.group(3));
			if (operation == 'O') {
				field.openTile(row, column);
			} else if (operation == 'M') {
				field.markTile(row, column);
			}
		} else if (input.length() == 1 && input.charAt(0) == 'X') {
			System.out.println("You choosed Exit");
			System.exit(0);
		} else {
			throw new WrongFormatException("Wrong input " + input);
		}
	}

}
