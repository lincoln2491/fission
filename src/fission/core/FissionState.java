package fission.core;

import ai.interfaces.AbstractState;

public class FissionState extends AbstractState {
	private FieldColor board[][];

	public String toString() {
		String result = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				FieldColor tmp = board[i][j];
				if (tmp == FieldColor.EMPTY) {
					result += "-";
				}
				if (tmp == FieldColor.WHITE) {
					result += "w";
				}
				if (tmp == FieldColor.BLACK) {
					result += "b";
				}
			}
		}
		return result;
	}

	public FieldColor[][] getBoard() {
		return board;
	}

	public void setBoard(FieldColor aBoard[][]) {
		board = aBoard;
	}
}
