package TicTacToe.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import TicTacToe.core.Tile.State;

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
			if (field.getState() == GameState.SOLVED) {
				System.out.println("Congrat. ");
				System.exit(0);
			} else if (field.getState() == GameState.DRAW) {
				System.out.println("It is DRAW. ");
				System.exit(0);
			}
			processInput();
		} while (true);
	}

	/**
	 * Updates user interface - prints the field.
	 */
	@Override
	public void update() {
		System.out.print("   ");
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.printf("%2d ", column);
		}
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.println("");
			System.out.printf("%2c ", 'A' + row);
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile.getState() == State.X) {
					System.out.printf("%2c ", 'X');
				}
				else if (tile.getState() == State.O) {
					System.out.printf("%2c ", 'O');
				} else if (tile.getState() == State.CLOSED) {
					System.out.printf("%2c ", '-');
				}
			}
		}
		System.out.println("");
		System.out.println("Please enter your selection: napr. B4 ");
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {

		String usersInput = readLine().toUpperCase();

		try {
			handleInput(usersInput);
		} catch (WrongFormatException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private void handleInput(String input) throws WrongFormatException {

		Pattern pattern = Pattern.compile("([A-Z])([0-9]+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {

			char rowAsChar = matcher.group(1).charAt(0);
			int row = rowAsChar - 'A';
			int column = Integer.parseInt(matcher.group(2));

			field.openTile(row, column, field.valueOfLastTile);


		} else {
			throw new WrongFormatException("Wrong input " + input);
		}
	}

}
