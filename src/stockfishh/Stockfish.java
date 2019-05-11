package stockfishh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;

/**
 * A simple and efficient client to run Stockfish from Java
 * 
 * @author Rahul A R
 * 
 */
public class Stockfish {
	private Process engineProcess;
	private BufferedReader processReader;
	private OutputStreamWriter processWriter;

	private static final String PATH = "stockfish_9_x64.exe";

	/**
	 * Starts Stockfish engine as a process and initializes it
	 * 
	 * @param None
	 * @return True on success. False otherwise
	 */
	public boolean startEngine() {
		try {
			engineProcess = Runtime.getRuntime().exec(PATH);
			processReader = new BufferedReader(new InputStreamReader(engineProcess.getInputStream()));
			processWriter = new OutputStreamWriter(engineProcess.getOutputStream());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Takes in any valid UCI command and executes it
	 * 
	 * @param command
	 */
	public void sendCommand(String command) {
		try {
			processWriter.write(command + "\n");
			processWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is generally called right after 'sendCommand' for getting the raw output
	 * from Stockfish
	 * 
	 * @param waitTime Time in milliseconds for which the function waits before
	 *                 reading the output. Useful when a long running command is
	 *                 executed
	 * @return Raw output from Stockfish
	 */
	public String getOutput(int waitTime) {
		StringBuffer buffer = new StringBuffer();
		try {
			Thread.sleep(waitTime);
			sendCommand("isready");
			while (true) {
				String text = processReader.readLine();
				if (text.equals("readyok"))
					break;
				else
					buffer.append(text + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public String getOutput() {
		StringBuffer buffer = new StringBuffer();
		try {
			String text = " ";

			while (!text.contains("bestmove")) {
				text = processReader.readLine();
				buffer.append(text + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * This function returns the best move for a given position after calculating
	 * for 'waitTime' ms
	 * 
	 * @param fen      Position string
	 * @param waitTime in milliseconds
	 * @return Best Move in PGN format
	 */
	public String getBestMove(String fen, int depth) {
		sendCommand("position fen " + fen);
		sendCommand("go depth " + depth);
		String bestmove = getOutput();

		bestmove = StringUtils.substringBetween(bestmove, "bestmove ", " ponder");
		return bestmove;
	}

	/**
	 * Stops Stockfish and cleans up before closing it
	 */
	public void stopEngine() {
		try {
			sendCommand("quit");
			processReader.close();
			processWriter.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Get a list of all legal moves from the given position
	 * 
	 * @param fen Position string
	 * @return String of moves
	 */
	public String getLegalMoves(String fen) {
		sendCommand("position fen " + fen);
		sendCommand("d");
		return getOutput(0).split("Legal moves: ")[1];
	}

	/**
	 * Draws the current state of the chess board
	 * 
	 * @param fen Position string
	 */
	public void drawBoard(String fen) {
		sendCommand("position fen " + fen);
		sendCommand("d");

		String[] rows = getOutput(0).split("\n");

		for (int i = 1; i < 18; i++) {
			System.out.println(rows[i]);
		}
	}

	/**
	 * Get the evaluation score of a given board position
	 * 
	 * @param fen      Position string
	 * @param waitTime in milliseconds
	 * @return evalScore
	 * @throws IOException
	 */
	public float getEvalScore(String fen, int depth) throws IOException {
		sendCommand("position fen " + fen);
		sendCommand("go depth " + depth);
		String k = "ff";
		float evalScore = 0.0f;
		String dump = getOutput();
		BufferedReader bufReader = new BufferedReader(new StringReader(dump));
		String line = "ff";
		while (!line.startsWith("info depth " + depth)) {
			line = bufReader.readLine();
		}
		line = StringUtils.substringBetween(line, "score cp", "nodes");

		/*
		 * 
		 * 
		 * line=StringUtils.substringBetween(line, "score cp", "nodes");
		 */

		return Float.parseFloat(line) / 100;
	}

	/*
	 * public float getEvalScore(String fen, int depth) {
	 * sendCommand("position fen " + fen); sendCommand("go depth " + depth);
	 * 
	 * float evalScore = 0.0f; String d=getevalOutput(); String[] dump =
	 * d.split("\n"); for (int i = dump.length - 1; i >= 0; i--) { if
	 * (dump[i].startsWith("info depth ")) { try { evalScore =
	 * Float.parseFloat(StringUtils.substringBetween(dump[i],"score cp "," nodes"));
	 * }catch(Exception e) {} } } return evalScore/100; }
	 */
	public static void main(String[] args) throws IOException {

		Stockfish o = new Stockfish();
		o.startEngine();
		o.sendCommand("uci");
		o.sendCommand("ucinewgame");
		System.out.println("ffff");

		System.out.println(o.getBestMove("rnbqkbnr/p1p4p/4p3/1p4p1/2P2P2/8/PP1PQ1PP/RNB1KBNR b KQkq - 1 61", 10));
	}
}