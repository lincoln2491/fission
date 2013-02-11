package ai.interfaces;

public interface GameIf {
	public void createNewGame();

	public void doMove(AbstractMove aMove);

	public AbstractState getState();

	public AbstractMove[] getAllMoves();

	public AbstractState getsState();
}
