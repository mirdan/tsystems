package TicTacToe.core;

public class Field {

	private final Tile[][] tiles;

	private final int rowCount;
	private final int columnCount;
	int valueOfLastTile = 0;

	private GameState state = GameState.PLAYING;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;

		tiles = new Tile[rowCount][columnCount];

		generate();
	}
	
	public int getValueOfLastTile() {
		return valueOfLastTile;
	}

	public void setValueOfLastTile(int valueOfLastTile) {
		this.valueOfLastTile = valueOfLastTile;
	}

	private int rowLastTile;
	private int columnLastTile;

	Tile lastTile = new Tile();

	
	public void openTile(int row, int column, int valueOfLastTile) {
		Tile tile = tiles[row][column];

		if (tile.getState() == Tile.State.CLOSED) {
			if (valueOfLastTile == 0) {
				tile.setState(Tile.State.X);
				setValueOfLastTile(1);
				rowLastTile = row;
				columnLastTile = column;
				//lastTile = tile;
				lastTile.setState(Tile.State.X);

			} else if (valueOfLastTile == 1) {
				tile.setState(Tile.State.O);
				setValueOfLastTile(0);
				rowLastTile = row;
				columnLastTile = column;
				//lastTile = tile;
				lastTile.setState(Tile.State.O);


			}
			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			} else if (isDraw()) {
				state = GameState.DRAW;
				return;
			}
		}
	}

	private void generate() {

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				tiles[i][j] = new Tile();
			}
		}
	}

	private boolean isSolved() {
		if (countTiles(-1, 0, rowLastTile, columnLastTile, lastTile) >= 4)
			return true;
		if (countTiles(0, -1, rowLastTile, columnLastTile, lastTile) >= 4)
			return true;
		if (countTiles(-1, -1, rowLastTile, columnLastTile, lastTile) >= 4)
			return true;
		if (countTiles(-1, 1, rowLastTile, columnLastTile, lastTile) >= 4)
			return true;
		return false;
	}

	private int countTiles(int deltaRow, int deltaColumn, int row, int column, Tile lastTile) {
		int counter = 0;
		counter = countTilesWithLoop(deltaRow, deltaColumn, row, column, lastTile, counter);
		deltaRow = -deltaRow;
		deltaColumn = -deltaColumn;
		counter = countTilesWithLoop(deltaRow, deltaColumn, row, column, lastTile, counter);
		return counter;
	}

	private int countTilesWithLoop(int deltaRow, int deltaColumn, int row, int column, Tile lastTile, int counter) {
		int actRow = row + deltaRow;
		int actColumn = column + deltaColumn;
		while (actRow >= 0 && actRow < getRowCount() && actColumn >= 0 && actColumn < getColumnCount()) {
			Tile tile = tiles[actRow][actColumn];
			if (tile.getState() == lastTile.getState())
				counter++;
			else
				break;
			actRow +=  deltaRow;
			actColumn += deltaColumn;
		}
		return counter;
	}

	private boolean isDraw() {
		if (getNumberOf(Tile.State.CLOSED) == 0) {
			return true;
		}
		return false;
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

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

}
