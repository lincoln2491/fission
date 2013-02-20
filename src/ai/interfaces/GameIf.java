package ai.interfaces;

import java.util.ArrayList;

public interface GameIf {

	public boolean isWhitePlayerTurn();

	public void doMove(AbstractMove aMove);

	public AbstractState getActualState();

	public ArrayList<AbstractMove> getAllMoves();

	public ArrayList<AbstractMove> getAllMovesFromState(AbstractState aState,
			boolean aIsForWhite);

	public void setState(AbstractState aAbstractState);

	public AbstractState getStateAfterMove(AbstractState aState,
			AbstractMove aMove);

}
