package fission.core;

import java.util.Iterator;

import ai.interfaces.GameIf;
import ai.interfaces.AbstractMove;
import ai.interfaces.AbstractState;

public class FissionGame implements GameIf {
	private FissionState actualState;

	public FissionGame() {
		createNewGame();
	}

	@Override
	public void doMove(AbstractMove aMove) {
		int x = ((FissionMove) aMove).getX();
		int y = ((FissionMove) aMove).getY();
		int xDirection = ((FissionMove) aMove).getxDirection();
		int yDirection = ((FissionMove) aMove).getyDirection();
		int xTarget = x;
		int yTarget = y;
		boolean isStopedByWall = true;
		FieldColor board[][] = actualState.getBoard();

		if (board[x][y] == FieldColor.EMPTY) {
			return;
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
		if (!isStopedByWall) {
			board[xTarget - 1][yTarget - 1] = FieldColor.EMPTY;
			board[xTarget - 1][yTarget + 1] = FieldColor.EMPTY;
			board[xTarget + 1][yTarget - 1] = FieldColor.EMPTY;
			board[xTarget + 1][yTarget + 1] = FieldColor.EMPTY;
			board[xTarget - 1][yTarget] = FieldColor.EMPTY;
			board[xTarget + 1][yTarget] = FieldColor.EMPTY;
			board[xTarget][yTarget - 1] = FieldColor.EMPTY;
			board[xTarget][yTarget + 1] = FieldColor.EMPTY;
		}
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

	@Override
	public void createNewGame() {
		actualState = new FissionState();
		FieldColor board[][] = new FieldColor[8][8];

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
	public AbstractState getState() {
		return actualState;
	}

	@Override
	public AbstractMove[] getAllMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractState getsState() {
		return actualState;
	}

}
