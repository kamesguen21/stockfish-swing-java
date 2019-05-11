package stockfishh.libchess;

public enum PieceType {

	PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING, NONE;

	public static PieceType fromValue(String v) {
		return valueOf(v);
	}

	public String value() {
		return name();
	}
}
