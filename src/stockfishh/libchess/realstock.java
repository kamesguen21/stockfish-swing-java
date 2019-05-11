package stockfishh.libchess;

import java.io.*;

public class realstock {
	private Process engineProcess;
	private BufferedReader processReader;
	private OutputStreamWriter processWriter;

	private static final String PATH = "C:\\Users\\kames\\OneDrive\\Desktop\\chess\\stockfish-9-win\\Windows\\stockfish_9_x64.exe";

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
}
