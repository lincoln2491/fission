package fission.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

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
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
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
			System.out.println();
		}

	}

	public void createNewGame(boolean aIsWhitePlayer) {
		actualState = new FissionState();
		FieldColor board[][] = new FieldColor[8][8];
		actualState.setWhitePlayerTurn(aIsWhitePlayer);

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
		return getAllMovesFromState(actualState,
				actualState.isWhitePlayerTurn());
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
		FissionState returnState = new FissionState();
		FieldColor board[][] = new FieldColor[8][8];
		FieldColor tmpBoard[][] = ((FissionState) aState).getBoard();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = tmpBoard[i][j];
			}
		}

		if (board[x][y] == FieldColor.EMPTY) {
			returnState.setBoard(board);
			returnState.setWhitePlayerTurn(!actualState.isWhitePlayerTurn());
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
			// xTarget += xDirection;
			// yTarget += yDirection;
			if (xTarget > 0 && yTarget > 0) {
				board[xTarget - 1][yTarget - 1] = FieldColor.EMPTY;
			}
			if (xTarget > 0 && yTarget < 7) {
				board[xTarget - 1][yTarget + 1] = FieldColor.EMPTY;
			}
			if (xTarget < 7 && yTarget > 0) {
				board[xTarget + 1][yTarget - 1] = FieldColor.EMPTY;
			}
			if (xTarget < 7 && yTarget < 7) {
				board[xTarget + 1][yTarget + 1] = FieldColor.EMPTY;
			}
			if (xTarget > 0) {
				board[xTarget - 1][yTarget] = FieldColor.EMPTY;
			}
			if (xTarget < 7) {
				board[xTarget + 1][yTarget] = FieldColor.EMPTY;
			}
			if (yTarget > 0) {
				board[xTarget][yTarget - 1] = FieldColor.EMPTY;
			}
			if (yTarget < 7) {
				board[xTarget][yTarget + 1] = FieldColor.EMPTY;
			}
			board[xTarget][yTarget] = FieldColor.EMPTY;
		}

		returnState.setBoard(board);
		returnState.setWhitePlayerTurn(!actualState.isWhitePlayerTurn());
		// printState();
		return returnState;
	}

	@Override
	public ArrayList<AbstractMove> getAllMovesFromState(AbstractState aState,
			boolean aIsForWhite) {
		ArrayList<AbstractMove> moves = new ArrayList<AbstractMove>();
		FieldColor[][] board = ((FissionState) aState).getBoard();
		boolean isWhiteTurn = aIsForWhite;
		int numberOfWhitePlayers = 0;
		int numberOfBlackPlayers = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				// sprawdza, czy są pionki
				if (board[i][j] == FieldColor.WHITE) {
					numberOfWhitePlayers++;
				} else if (board[i][j] == FieldColor.BLACK) {
					numberOfBlackPlayers++;
				}

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
					if (j < 7 && board[i][j + 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, 0, 1));
					}
					if (i > 0 && j < 7
							&& board[i - 1][j + 1] == FieldColor.EMPTY) {
						moves.add(new FissionMove(i, j, -1, 1));
					}
				}
			}
		}
		if ((numberOfWhitePlayers == 1 && numberOfBlackPlayers == 1)
				|| numberOfWhitePlayers == 0 || numberOfBlackPlayers == 0) {
			moves.clear();
		}

		long seed = System.nanoTime();
		// Collections.shuffle(moves, new Random(seed));
		return moves;
	}

	@Override
	public boolean isWhitePlayerTurn() {
		return actualState.isWhitePlayerTurn();
	}

	public void nextComputerPlayerMove() {
		FissionMove move;
		if (actualState.isWhitePlayerTurn()) {
			move = (FissionMove) whitePlayer.getNextMove();
		} else {
			move = (FissionMove) blackPlayer.getNextMove();
		}
		doMove(move);
	}

	public void setWhitePlayerTurn(boolean aWhitePlayerTurn) {
		actualState.setWhitePlayerTurn(aWhitePlayerTurn);
	}

	@Override
	public int whoIsWinner() {
		int numberOfWhite = 0;
		int numberOfBlack = 0;
		FieldColor board[][] = actualState.getBoard();
		int numberOfMoves = getAllMoves().size();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == FieldColor.WHITE) {
					numberOfWhite++;
				}
				if (board[i][j] == FieldColor.BLACK) {
					numberOfBlack++;
				}
			}
		}

		if ((numberOfBlack == 0 && numberOfWhite == 0)
				|| (numberOfBlack == 1 && numberOfWhite == 1)) {
			return -1;
		}

		if (numberOfBlack == 0) {
			return 1;
		}

		if (numberOfWhite == 0) {
			return 2;
		}

		if (numberOfMoves == 0) {
			return -1;
		}

		return 0;
	}

	@Override
	public int whoIsWinnerFromState(AbstractState aState, boolean aWhiteTurn) {
		int numberOfWhite = 0;
		int numberOfBlack = 0;
		FieldColor board[][] = ((FissionState) aState).getBoard();
		int numberOfMoves = getAllMovesFromState( aState, aWhiteTurn).size();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == FieldColor.WHITE) {
					numberOfWhite++;
				}
				if (board[i][j] == FieldColor.BLACK) {
					numberOfBlack++;
				}
			}
		}

		if ((numberOfBlack == 0 && numberOfWhite == 0)
				|| (numberOfBlack == 1 && numberOfWhite == 1)) {
			return -1;
		}

		if (numberOfBlack == 0) {
			return 1;
		}

		if (numberOfWhite == 0) {
			return 2;
		}

		if (numberOfMoves == 0) {
			return -1;
		}

		return 0;
	}

}
