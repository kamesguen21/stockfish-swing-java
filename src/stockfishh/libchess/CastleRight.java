package stockfishh.libchess;

public enum CastleRight {
	KING_SIDE, QUEEN_SIDE, KING_AND_QUEEN_SIDE, NONE;

	public static CastleRight fromValue(String v) {
		return valueOf(v);
	}

	public String value() {
		return name();
	}
}
