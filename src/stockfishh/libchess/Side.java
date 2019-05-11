package stockfishh.libchess;

public enum Side {

	WHITE, BLACK;

	public static Side fromValue(String v) {
		return valueOf(v);
	}

	public String value() {
		return name();
	}

	public Side flip() {
		return Side.WHITE.equals(this) ? Side.BLACK : Side.WHITE;
	}
}
