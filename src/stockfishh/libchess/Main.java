package stockfishh.libchess;

import chesspresso.Chess;
import chesspresso.position.Position;
import chesspresso.move.Move;
import chesspresso.move.IllegalMoveException;

public class Main {

	static Position position;
	static String[] game;

	static final int MAX_PLY = 9;

	// Don't bother handling exceptions.
	public static void main(String[] args) throws Exception {
		// The initial position.
		position = Position.createInitialPosition();

		// Array of moves.
		game = new String[MAX_PLY + 1];

		// First move: e4.
		// Boolean input (third parameter) describes whether it's a capture.
		short firstmove = Move.getRegularMove(Chess.E2, Chess.E4, false);

		// Alter the position by making this move.
		position.doMove(firstmove);
		System.out.println(position.isCastlePossible(MAX_PLY));
	}
}
