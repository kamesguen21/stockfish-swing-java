package stockfishh.libchess;

/**
 * Board Event Listener
 */
public interface BoardEventListener {

	/**
	 * On Move Event
	 *
	 * @param event
	 */
	void onEvent(BoardEvent event);

}