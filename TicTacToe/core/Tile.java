package TicTacToe.core;

public  class Tile {

	public enum State {
		 CLOSED,X,O
	}

	private State state = State.CLOSED;

	public State getState() {
		return state;
	}

	void setState(State state) {
		this.state = state;
	}

}