package fission.core;

import ai.interfaces.AbstractMove;

public class FissionMove extends AbstractMove {
	private int x;
	private int y;
	private int xDirection;
	private int yDirection;

	public String toString() {
		return "Move from (" + x + " , " + y + " ) direction ( " + xDirection
				+ " , " + yDirection + " )";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getxDirection() {
		return xDirection;
	}

	public int getyDirection() {
		return yDirection;
	}

	public FissionMove(int aX, int aY, int aXDirection, int aYDirection) {
		x = aX;
		y = aY;
		xDirection = aXDirection;
		yDirection = aYDirection;
	}

}
