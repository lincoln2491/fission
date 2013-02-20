package fission.core;

import ai.interfaces.AbstractState;

public class FissionState extends AbstractState {
	private FieldColor board[][];


	
	public FieldColor[][] getBoard() {
		return board;
	}

	public void setBoard(FieldColor aBoard[][]) {
		board = aBoard;
	}
}
