package stockfishh.libchess;

/**
 * Board Event Types
 */
public enum BoardEventType {
	ON_MOVE, ON_UNDO_MOVE, ON_LOAD;

	public static BoardEventType fromValue(String v) {
		return valueOf(v);
	}

	public String value() {
		return name();
	}
}
