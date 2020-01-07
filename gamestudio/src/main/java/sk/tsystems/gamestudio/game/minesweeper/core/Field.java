package sk.tsystems.gamestudio.game.minesweeper.core;

import java.util.Random;

import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;

public class Field {
	private final Tile[][] tiles;
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	private final long startMillis = System.currentTimeMillis();

	
	private GameState state = GameState.PLAYING;
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			} else if (tile instanceof Clue) {
				Clue clue = (Clue) tiles[row][column];
				if (clue.getValue() == 0) {
					openAdjacentTiles(row, column);
				}
			}
			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
		}
	}


	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */

	private void generate() {
		Random randomNumber = new Random();
		int counter = 0;
		while (counter < mineCount) {
			int randomRow = randomNumber.nextInt(rowCount);
			int randomColumn = randomNumber.nextInt(columnCount);
			if (tiles[randomRow][randomColumn] == null) {
				tiles[randomRow][randomColumn] = new Mine();
				counter++;
			}
		}
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (tiles[i][j] == null) {
					tiles[i][j] = new Clue(countAdjacentMines(i, j));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	public boolean isSolved() {
		return (((rowCount * columnCount) - getNumberOf(Tile.State.OPEN)) == mineCount); 
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row    row number.
	 * @param column column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Clue) {
							openTile(actRow, actColumn);
						}
					}
				}
			}
		}
	}

	private int getNumberOf(Tile.State state) {
		int count = 0;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column].getState() == state) {
					count++;
				}
			}
		}
		return count;
	}

	public int getScore() {
		int seconds = (int) (System.currentTimeMillis() - startMillis) / 1000;
		int score = 1000 - seconds;
		if (score > 0) return score;
		else return 0;
	}
	
	
	
	public int getRemainingMineCount() {
		return (mineCount - getNumberOf(State.MARKED));
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

}
