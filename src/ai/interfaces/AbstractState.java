package ai.interfaces;

public abstract class AbstractState {
	boolean isWhitePlayer;

	public boolean isWhitePlayer() {
		return isWhitePlayer;
	}

	public void setWhitePlayer(boolean isWhitePlayer) {
		this.isWhitePlayer = isWhitePlayer;
	}

}
