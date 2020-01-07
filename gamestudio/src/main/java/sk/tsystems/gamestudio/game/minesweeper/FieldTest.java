package sk.tsystems.gamestudio.game.minesweeper;

import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;

class FieldTest {

	static final int ROWS = 9;
	static final int COLUMNS = 9;
	static final int MINES = 10;
	private Field field;

//	@BeforeEach
//	void init() {
//		field = new Field(10, 10, MINES);
//	}
//
//	@Test
//	@DisplayName("Field should cointain correct number of mines")
//
//	void fieldShouldCointainCorrectNumberOfMines() {
//		int mineCount = 0;
//		for (int row = 0; row < field.getRowCount(); row++) {
//			for (int column = 0; column < field.getColumnCount(); column++) {
//				if (field.getTile(row, column) instanceof Mine) {
//					mineCount++;
//				}
//			}
//		}
//		assertEquals(10, mineCount, String.format("There should be 10 mines in the field", MINES));
//	}
//
//	@Test
//	public void isSolved() {
//		Field field = new Field(ROWS, COLUMNS, MINES);
//
//		assertEquals(GameState.PLAYING, field.getState());
//
//		int open = 0;
//		for (int row = 0; row < field.getRowCount(); row++) {
//			for (int column = 0; column < field.getColumnCount(); column++) {
//				if (field.getTile(row, column) instanceof Clue) {
//					field.openTile(row, column);
//					open++;
//				}
//				if (field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
//					assertEquals(GameState.SOLVED, field.getState());
//				} else {
//					assertNotSame(GameState.FAILED, field.getState());
//				}
//			}
//		}
//
//		assertEquals(GameState.SOLVED, field.getState());
//	}

}
