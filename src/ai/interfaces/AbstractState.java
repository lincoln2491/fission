package ai.interfaces;

public abstract class AbstractState {
	boolean isWhitePlayerTurn;

	public boolean isWhitePlayerTurn() {
		return isWhitePlayerTurn;
	}

	public void setWhitePlayerTurn(boolean isWhitePlayerTurn) {
		this.isWhitePlayerTurn = isWhitePlayerTurn;
	}

}
