package fission.core;

import java.util.ArrayList;
import java.util.Iterator;

import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;
import ai.interfaces.AbstractMove;
import ai.interfaces.AbstractState;

public class FissionGame implements GameIf {
	private FissionState actualState;
	ComputerPlayerIf whitePlayer;
	ComputerPlayerIf blackPlayer;

	public ComputerPlayerIf getWhitePlayer() {
		return whitePlayer;
	}

	public FieldColor fieldColorAt(int aX, int aY) {
		return actualState.getBoard()[aX][aY];
	}

	public void setWhitePlayer(ComputerPlayerIf aWhitePlayer) {
		whitePlayer = aWhitePlayer;
	}

	public ComputerPlayerIf getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(ComputerPlayerIf aBlackPlayer) {
		blackPlayer = aBlackPlayer;
	}

	public FissionGame(FissionState aActualState) {
		actualState = aActualState;
	}

	public FissionGame(boolean aIsWhitePlayer) {
		createNewGame(aIsWhitePlayer);
	}

	@Override
	public void doMove(AbstractMove aMove) {
		actualState = (FissionState) getStateAfterMove(actualState, aMove);
	}

	public void printState() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				FieldColor tmp = actualState.getBoard()[i][j];
				if (tmp == FieldColor.EMPTY) {
					System.out.print("-");
				}
				if (tmp == FieldColor.WHITE) {
					System.out.print("w");
				}
				if (tmp == FieldColor.BLACK) {
					System.out.print("b");
				}
			}
			System.out.println("");
		}
	}

	public void createNewGame(boolean aIsWhitePlayer) {
		actualState = new FissionState();
		FieldColor board[][] = new FieldColor[8][8];
		actualState.setWhitePlayer(aIsWhitePlayer);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i == 0) || (j == 0) || (i == 1 && j == 1)
						|| (i == 2 && j == 1) || (i == 1 && j == 2)) {
					board[i][j] = FieldColor.EMPTY;
				} else if ((i == 3 && j == 1) || (i == 2 && j == 2)
						|| (i == 1 && j == 3) || (i == 3 && j == 3)) {
					board[i][j] = FieldColor.WHITE;
				} else if ((i == 2 && j == 3) || (i == 3 && j == 2)) {
					board[i][j] = FieldColor.BLACK;
				}
				if (i >= 4) {
					board[i][j] = board[7 - i][j].getReverse();
				} else if (j >= 4 && i < 4) {
					board[i][j] = board[i][7 - j].getReverse();
				}

			}
		}

		actualState.setBoard(board);
	}

	@Override
	public AbstractState getActualState() {
		return actualState;
	}

	@Override
	public ArrayList<AbstractMove> getAllMoves() {
		return getAllMovesFromState(actualState, actualState.isWhitePlayer());
	}

	@Override
	public void setState(AbstractState aAbstractState) {
		actualState = (FissionState) aAbstractState;
	}

	@Override
	public AbstractState getStateAfterMove(AbstractState aState,
			AbstractMove aMove) {
		int x = ((FissionMove) aMove).getX();
		int y = ((FissionMove) aMove).getY();
		int xDirection = ((FissionMove) aMove).getxDirection();
		int yDirection = ((FissionMove) aMove).getyDirection();
		int xTarget = x;
		int yTarget = y;
		boolean isStopedByWall = true;
		FieldColor board[][] = actualState.getBoard().clone();
		FissionState returnState = new FissionState();

		if (board[x][y] == FieldColor.EMPTY) {
			returnState.setBoard(board);
			return returnState;
		}

		while (true) {
			if (xTarget + xDirection == 8 || xTarget + xDirection == -1
					|| yTarget + yDirection == 8 || yTarget + yDirection == -1) {
				isStopedByWall = true;
				break;
			}

			FieldColor nextField = board[xTarget + xDirection][yTarget
					+ yDirection];
			if (nextField == FieldColor.WHITE || nextField == FieldColor.BLACK) {
				isStopedByWall = false;
				break;
			}

			xTarget += xDirection;
			yTarget += yDirection;
		}

		board[xTarget][yTarget] = board[x][y];
		board[x][y] = FieldColor.EMPTY;
		if (!isStopedByWall) {// TODO wykroczenia poza tablice
			board[xTarget - 1][yTarget - 1] = FieldColor.EMPTY;
			board[xTarget - 1][yTarget + 1] = FieldColor.EMPTY;
			board[xTarget + 1][yTarget - 1] = FieldColor.EMPTY;
			board[xTarget + 1][yTarget + 1] = FieldColor.EMPTY;
			board[xTarget - 1][yTarget] = FieldColor.EMPTY;
			board[xTarget + 1][yTarget] = FieldColor.EMPTY;
			board[xTarget][yTarget - 1] = FieldColor.EMPTY;
			board[xTarget][yTarget + 1] = FieldColor.EMPTY;
		}
		returnState.setBoard(board);
		return returnState;
	}

	@Override
	public ArrayList<AbstractMove> getAllMovesFromState(AbstractState aState,
			boolean aIsForWhite) {
		ArrayList<AbstractMove> moves = new ArrayList<AbstractMove>();
		FieldColor[][] board = ((FissionState) aState).getBoard();
		boolean isWhiteTurn = aIsForWhite;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((board[i][j] == FieldColor.BLACK && !isWhiteTurn)
						|| (board[i][j] == FieldColor.WHITE && isWhiteTurn)) {
					if (i > 0 && board[i - 1][j] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, -1, 0));
					}
					if (i > 0 && j > 0
							&& board[i - 1][j - 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, -1, -1));
					}
					if (j > 0 && board[i][j - 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, 0, -1));
					}
					if (i < 7 && j > 0
							&& board[i + 1][j - 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, 1, -1));
					}
					if (i < 7 && board[i + 1][j] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, 1, 0));
					}
					if (i < 7 && j < 7
							&& board[i + 1][j + 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, 1, 1));
					}
					if (j > 0 && board[i][j + 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, 0, 1));
					}
					if (i > 0 && j < 7
							&& board[i - 1][j + 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, -1, 1));
					}
				}
			}
		}
		return moves;
	}

	@Override
	public boolean isWhitePlayerTurn() {
		// TODO Auto-generated method stub
		return actualState.isWhitePlayer();
	}

}
