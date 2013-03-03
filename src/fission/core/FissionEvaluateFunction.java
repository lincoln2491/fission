package fission.core;

import ai.interfaces.AbstractEvaluationFunction;
import ai.interfaces.AbstractState;
import ai.interfaces.GameIf;

public class FissionEvaluateFunction extends AbstractEvaluationFunction {
	FissionGame game;

	public FissionEvaluateFunction(GameIf aFissionGame) {
		game = (FissionGame) aFissionGame;
	}

	@Override
	public int evaluateState(AbstractState aState, boolean aWhiteTurn) {
		FieldColor board[][] = ((FissionState) aState).getBoard();
		int winner = game.whoIsWinnerFromState(aState, aWhiteTurn);
		if (winner == -1) {
			return 0;
		} else if (winner == 1) {
			return Integer.MAX_VALUE;
		} else if (winner == 2) {
			return Integer.MIN_VALUE;
		} else {
			int result = 0;
			int numberOfBlack = 0;
			int numberOfWhite = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					int beatingWhite = 0;
					int beatingBlack = 0;
					FieldColor tmp;
					/*if (i > 0 && j > 0) {
						tmp = board[i - 1][j - 1];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}
					if (i > 0 && j < 7) {
						tmp = board[i - 1][j + 1];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}
					if (i < 7 && j > 0) {
						tmp = board[i + 1][j - 1];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}
					if (i < 7 && j < 7) {
						tmp = board[i + 1][j + 1];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}
					if (i > 0) {
						tmp = board[i - 1][j];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}
					if (i < 7) {
						tmp = board[i + 1][j];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}
					if (j > 0) {
						tmp = board[i][j - 1];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}
					if (j < 7) {
						tmp = board[i][j + 1];
						if (tmp == FieldColor.WHITE) {
							beatingWhite++;
						}
						if (tmp == FieldColor.BLACK) {
							beatingWhite++;
						}
					}*/

					if (board[i][j] == FieldColor.WHITE) {
						numberOfWhite ++;//+= 1 + 0.1 * (beatingBlack - beatingWhite);
					}
					if (board[i][j] == FieldColor.BLACK) {
						numberOfBlack ++;//= 0.1 * (beatingWhite - beatingBlack);
					}
				}
			}

			result = numberOfWhite - numberOfBlack;
			return result;
		}
	}
}
