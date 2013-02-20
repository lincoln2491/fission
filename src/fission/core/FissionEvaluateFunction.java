package fission.core;

import ai.interfaces.AbstractEvaluationFunction;
import ai.interfaces.AbstractMove;
import ai.interfaces.AbstractState;

public class FissionEvaluateFunction extends AbstractEvaluationFunction {

	@Override
	public int evaluateState(AbstractState aState) {
		FieldColor board[][] = ((FissionState) aState).getBoard();
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

		if (aState.isWhitePlayer()) {
			result = 100 * (numberOfWhite / numberOfBlack);
		} else {
			result = 100 * (numberOfBlack / numberOfWhite);
		}
		return result;
	}
}
