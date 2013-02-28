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
	public int evaluateState(AbstractState aState) {
		FieldColor board[][] = ((FissionState) aState).getBoard();
		int winner = game.whoIsWinner();
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
					if (board[i][j] == FieldColor.WHITE) {
						numberOfWhite++;
					}
					if (board[i][j] == FieldColor.BLACK) {
						numberOfBlack++;
					}
				}
			}

			result = numberOfWhite - numberOfBlack;
			return result;
		}
	}
}
