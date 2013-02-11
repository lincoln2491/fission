package fission.core;

public enum FieldColor {
	WHITE, BLACK, EMPTY;

	public FieldColor getReverse() {
		if (this == WHITE) {
			return BLACK;
		}
		if (this == BLACK) {
			return WHITE;
		}
		return EMPTY;
	}
}
