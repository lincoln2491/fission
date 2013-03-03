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

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o instanceof FissionMove) {
			FissionMove tmp = (FissionMove) o;
			if (tmp.getX() == this.getX() && tmp.getY() == this.getY()
					&& tmp.getxDirection() == this.getxDirection()
					&& tmp.getyDirection() == this.getyDirection()) {
				return true;
			}
		}
		return false;
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
