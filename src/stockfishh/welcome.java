package stockfishh;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import stockfishh.Stockfish;
import stockfishh.libchess.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohamed Khames Guen
 *
 */
public class welcome extends JFrame implements ActionListener {

	// private Board b=new Board();
	JPanel bord = new JPanel(new GridLayout(8, 8));
	JPanel controles = new JPanel(new GridLayout(0, 6, 4, 4));
	boolean castle = false;
	JPanel pieces = new JPanel(new GridLayout(8, 8));
	JPanel bordnumalph = new JPanel(new BorderLayout());
	JPanel info = new JPanel(new BorderLayout());
	JButton permut = null;
	JButton back = new JButton();
	JButton next = new JButton();
	JButton start = new JButton();
	JButton last = new JButton();
	JLabel nothing = new JLabel();
	JLabel nothing1 = new JLabel();
	boolean pgnclicked = false;
	MoveList pgnmoves = new MoveList();
	private JButton[][] chessBoardSquares = new JButton[8][8];
	List<ImageIcon> list1 = new ArrayList<ImageIcon>();
	public List<ImageIcon> list = new ArrayList<ImageIcon>();
	MoveList moves = new MoveList();
	Board board = new Board();
	JTextArea tx = new JTextArea(8, 30);
	JScrollPane scroll = new JScrollPane(tx);
	JPanel text = new JPanel(new BorderLayout());
	JPanel emptypane = new JPanel();
	JFrame f = new JFrame("Animation");

	boolean bestmoveclicked = false;
	JButton bestmove = new JButton("BEST MOVE");
	String best;
	JButton Dobestmove = new JButton("DO BEST MOVE");
	JButton score = new JButton("GET EVAL");
	JPanel bests = new JPanel(new GridLayout(3, 0));
	String promotionfrom[] = { "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "A2", "B2", "C2", "D2", "E2", "F2", "G2",
			"H2" };
	String promotionto[] = { "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "A1", "B1", "C1", "D1", "E1", "F1", "G1",
			"H1" };
	List<String> listpromotionfrom = new ArrayList<>();
	List<String> listpromotionto = new ArrayList<>();
	List<String> eval = new ArrayList<>();
	List<String> bestmoves = new ArrayList<>();
	List<String> notes = new ArrayList<>();

	boolean backtest = false;
	MoveList moveslist = new MoveList();
	MoveList legalmoveslist = new MoveList();
	int startcount = 0;
	boolean startclicked = false;
	int pgncounter = 0;
	int backcount = 0;
	ImageIcon whitemate = new ImageIcon("icons\\whiemate.png");
	ImageIcon blackmate = new ImageIcon("icons\\blackmate.png");
	ImageIcon stalemate = new ImageIcon("icons\\stalemate.png");
	ImageIcon InsufficientMaterial = new ImageIcon("icons\\InsufficientMaterial.png");
	ImageIcon backic = new ImageIcon("icons\\backwards.png");
	ImageIcon nextic = new ImageIcon("icons\\forward.png");
	ImageIcon lastic = new ImageIcon("icons\\finish.png");
	ImageIcon startic = new ImageIcon("icons\\start.png");
	ImageIcon pause = new ImageIcon("icons\\pause.png");
	ImageIcon chessbord = new ImageIcon("icons\\chessbord.png");
	Image img = chessbord.getImage();
	ImageIcon bishopd = new ImageIcon("icons\\Chess_bdt60.png");
	ImageIcon bishopl = new ImageIcon("icons\\Chess_blt60.png");
	ImageIcon kingd = new ImageIcon("icons\\Chess_kdt60.png");
	ImageIcon kingl = new ImageIcon("icons\\Chess_klt60.png");
	ImageIcon knightd = new ImageIcon("icons\\Chess_ndt60.png");
	ImageIcon knightl = new ImageIcon("icons\\Chess_nlt60.png");
	ImageIcon queenl = new ImageIcon("icons\\Chess_qlt60.png");
	ImageIcon queend = new ImageIcon("icons\\Chess_qdt60.png");
	ImageIcon rookl = new ImageIcon("icons\\Chess_rlt60.png");
	ImageIcon rookd = new ImageIcon("icons\\Chess_rdt60.png");
	ImageIcon pawnd = new ImageIcon("icons\\Chess_pdt60.png");
	ImageIcon pawnl = new ImageIcon("icons\\Chess_plt60.png");
	ImageIcon legals = new ImageIcon("icons\\legals.png");
	ImageIcon empty = new ImageIcon();
	ImageIcon alpha = new ImageIcon("icons\\alpha.png");
	ImageIcon num = new ImageIcon("icons\\num2.png");
	JLabel alp = new JLabel("     A      B      C       D       E      F      G       H");
	JLabel numm = new JLabel(
			"<html><br/>8<br/><br/>7<br/><br/>6<br/><br/>5<br/><br/>4<br/><br/>3<br/><br/>2<br/><br/>1<br/><br/></html>");

	/**
	 * @param img
	 * @return return type BufferedImage of image
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	/**
	 * @param im01
	 * @param im2
	 * @return the combination of two images
	 */
	public ImageIcon combiner(Icon im01, ImageIcon im2) {
		ImageIcon im1 = (ImageIcon) im01;
		Image ima1 = im1.getImage();
		Image ima2 = im2.getImage();
		BufferedImage image1 = toBufferedImage(ima1);
		BufferedImage image2 = toBufferedImage(ima2);
		int w = 60;
		int h = 60;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.drawImage(image2, 0, 0, null);
		g2.drawImage(image1, 0, 0, null);
		g2.dispose();
		ImageIcon newImg = new ImageIcon(image);
		return newImg;
	}

	/**
	 * @param bool
	 * @param b    puts a circle icon on the legal moves of the piece in the button
	 *             b
	 */
	public void colorlegalmoves(boolean bool, JButton b) {
		if (bool) {
			MoveList moves = null;
			try {
				moves = MoveGenerator.generateLegalMoves(board);
			} catch (MoveGeneratorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String s = "";
			for (Move i : moves) {
				if (i.toString().contains(squarename(b).toLowerCase())) {
					s += i.toString();
				}

			}
			s = s.replace((squarename(b).toLowerCase()), " ");
			System.out.println(moves);
			System.out.println(s);

			for (int i = 0; i < chessBoardSquares.length; i++) {
				for (int j = 0; j < chessBoardSquares[i].length; j++) {

					if (s.contains(squarename(chessBoardSquares[i][j]).toLowerCase())) {
						chessBoardSquares[i][j].setIcon(combiner(chessBoardSquares[i][j].getIcon(), legals));
					}
				}
			}

		}
	}

	/**
	 * @param s
	 * @return the move formate of string
	 */
	public Move stringtomove(String s) {
		String s1 = s.substring(0, 2);
		String s2 = s.substring(2, 4);
		s1 = s1.toUpperCase();
		s2 = s2.toUpperCase();

		Move v = new Move(Square.valueOf(s1), Square.valueOf(s2));
		return v;

	}

	/**
	 * @param f1
	 * @param f2
	 * @return the evaluation of a certain move
	 */
	public String analysis(float f1, float f2) {
		if (f1 == f2 || Math.abs(f1) - Math.abs(f2) < 0.3)
			return "Accurate";
		if (Math.abs(f1) - Math.abs(f2) >= 0.3 || Math.abs(f1) - Math.abs(f2) < 0.9)
			return "Inaccuracy";
		else if (Math.abs(f1) - Math.abs(f2) >= 0.9 || Math.abs(f1) - Math.abs(f2) < 2)
			return "Mistake";
		else if (Math.abs(f1) - Math.abs(f2) >= 2)
			return "Blunder";

		return null;

	}

	/**
	 * create the menubar with exit and pgn
	 */
	private void createMenuBar() {

		JMenuBar menubar = new JMenuBar();
		ImageIcon exitIcon = new ImageIcon("src/main/resources/exit.png");
		ImageIcon newIcon = new ImageIcon("src/main/resources/new.png");

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit", exitIcon);
		JMenuItem pgn = new JMenuItem("pgn");
		JMenuItem newMenuItem = new JMenuItem("New", newIcon);

		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				board = new Board();
				fillpiecestolist(list1, board.getFen());
				filllisttoarray(list1);
				moveslist = new MoveList();
				;
				pgnclicked = false;
			}
		});
		pgn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				PgnHolder pgn = null;
				if (pgnclicked) {
					board = new Board();
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
					moveslist = new MoveList();
				}
				try {
					pgn = new PgnHolder(PickAFile());
					pgn.loadPgn();
					for (Game game : pgn.getGame()) {
						game.loadMoveText();

						pgnmoves = game.getHalfMoves();
						// Replay all the moves from the game and print the final position in FEN format

					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				if (pgn != null) {
					pgnclicked = true;
				}
				try {

					tx.setText(pgnmoves.toSan());

				} catch (MoveConversionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stockfish o = new Stockfish();
				o.startEngine();
				o.sendCommand("uci");
				o.sendCommand("ucinewgame");
				int h = 0;
				board = new Board();
				float m = 0.0f;
				float k = 0.0f;
				for (Move i : pgnmoves) {

					try {
						k = o.getEvalScore(board.getFen(), 10);
					} catch (ArrayIndexOutOfBoundsException e) {
					} catch (IOException e) {
					} catch (Exception e) {
					}
					String s = o.getBestMove(board.getFen(), 10);
					eval.add(Float.toString(k));
					bestmoves.add(s);
					board.doMove(i);
				}
				board = new Board();
				int j = 0;
				for (Move i : pgnmoves) {
					if (i == pgnmoves.get(0)) {
						j++;
						board.doMove(stringtomove(bestmoves.get(0)));
						try {
							m = o.getEvalScore(board.getFen(), 10);
						} catch (Exception e) {
						}
						notes.add(analysis(m, Float.valueOf(eval.get(j))));
						System.out.println("m is" + m + "k is" + eval.get(j) + "j is " + j);
						board.undoMove();
						board.doMove(i);
					} else {
						try {
							board.doMove(stringtomove(bestmoves.get(j)));
						} catch (NullPointerException e) {
						}
						try {
							m = o.getEvalScore(board.getFen(), 10);
						} catch (Exception e) {
						}
						notes.add(analysis(m, Float.valueOf(eval.get(j - 1))));
						System.out.println("m is" + m + "k is" + eval.get(j - 1) + "j is " + j + "analysis is"
								+ analysis(m, Float.valueOf(eval.get(j))));
						board.undoMove();
						board.doMove(i);
						j++;
					}
				}
				board = new Board();

			}
		});
		file.add(newMenuItem);
		file.add(pgn);
		file.add(eMenuItem);
		menubar.add(file);

		setJMenuBar(menubar);
	}

	/**
	 * @return the file from the file dialog window
	 */
	public String PickAFile() {

		FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		String file = dialog.getDirectory() + dialog.getFile();
		return file;

	}

	/**
	 * @param h  the from square name
	 * @param h1 the to square name
	 * @param b  button reprisenting the square and the piece
	 * @return true if the square are in the last two rows and if the piece is pawn
	 */
	public String ispromotion(String h, String h1, JButton b) {
		for (String s : promotionfrom) {
			listpromotionfrom.add(s);
		}
		for (String s : promotionto) {
			listpromotionto.add(s);
		}

		if (listpromotionfrom.contains(h) && listpromotionto.contains(h1)) {
			if (b.getIcon() == pawnl)
				return "l";
			if (b.getIcon() == pawnd)
				return "d";
		}
		return "f";
	}

	/**
	 * sets up the controls of the moves and the stockfish options
	 */
	public void setupcontrols() {
		controles.setPreferredSize(new Dimension(250, 60));
		controles.add(nothing);
		controles.add(start);
		controles.add(back);
		controles.add(next);
		controles.add(last);
		controles.add(nothing1);

		controles.setBorder(BorderFactory.createEtchedBorder());
		back.setPreferredSize(new Dimension(10, 10));
		next.setPreferredSize(new Dimension(10, 10));
		start.setPreferredSize(new Dimension(10, 10));
		last.setPreferredSize(new Dimension(10, 10));
		back.setIcon(backic);
		next.setIcon(nextic);
		start.setIcon(startic);
		last.setIcon(lastic);
		back.setOpaque(false);
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);
		next.setOpaque(false);
		next.setContentAreaFilled(false);
		next.setBorderPainted(false);
		start.setOpaque(false);
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);
		last.setOpaque(false);
		last.setContentAreaFilled(false);
		last.setBorderPainted(false);
		bests.add(bestmove);
		bests.add(Dobestmove);
		bests.add(score);
	}

	/**
	 * returns a string reprisenting the square the button is on so we can add to
	 * the board class
	 */
	public String squarename(JButton b) {
		String x = "", y = "";
		for (int i = 0; i < chessBoardSquares.length; i++) {
			for (int j = 0; j < chessBoardSquares[i].length; j++) {
				if (chessBoardSquares[i][j] == b) {
					switch (j) {
					case 0:
						y = "8";
						break;
					case 1:
						y = "7";
						break;
					case 2:
						y = "6";
						break;
					case 3:
						y = "5";
						break;
					case 4:
						y = "4";
						break;
					case 5:
						y = "3";
						break;
					case 6:
						y = "2";
						break;
					case 7:
						y = "1";
						break;
					}
					switch (i) {
					case 0:
						x = "A";
						break;
					case 1:
						x = "B";
						break;
					case 2:
						x = "C";
						break;
					case 3:
						x = "D";
						break;
					case 4:
						x = "E";
						break;
					case 5:
						x = "F";
						break;
					case 6:
						x = "G";
						break;
					case 7:
						x = "H";
						break;
					}
					break;
				}

			}
		}

		return x + y;

	}

	/**
	 * fills the bord panel with the squares alterating between dark and light
	 * squares
	 */
	public void fillboard(JPanel l1) {
		for (int i = 0; i < chessBoardSquares.length; i++) {
			for (int j = 0; j < chessBoardSquares[i].length; j++) {
				JButton b = new JButton();
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				if ((j % 2 == 1 && i % 2 == 1)
						// ) {
						|| (j % 2 == 0 && i % 2 == 0)) {
					b.setBackground(Color.decode("#f5fffa"));
				} else {
					b.setBackground(Color.decode("#4169e1"));
				}
				chessBoardSquares[j][i] = b;

				l1.add(chessBoardSquares[j][i]);
			}
		}
	}

	/**
	 * returns the fen reprisantation of the board
	 */
	public String bordtofen() {
		String s = "";
		for (int i = 0; i < chessBoardSquares.length; i++) {
			for (int j = 0; j < chessBoardSquares[i].length; j++) {
				if (chessBoardSquares[j][i].getIcon() == pawnd)
					s += "p";
				else if (chessBoardSquares[j][i].getIcon() == pawnl)
					s += "P";
				else if (chessBoardSquares[j][i].getIcon() == rookl)
					s += "R";
				else if (chessBoardSquares[j][i].getIcon() == rookd)
					s += "r";
				else if (chessBoardSquares[j][i].getIcon() == knightl)
					s += "N";
				else if (chessBoardSquares[j][i].getIcon() == knightd)
					s += "n";
				else if (chessBoardSquares[j][i].getIcon() == kingd)
					s += "k";
				else if (chessBoardSquares[j][i].getIcon() == kingl)
					s += "K";
				else if (chessBoardSquares[j][i].getIcon() == queenl)
					s += "Q";
				else if (chessBoardSquares[j][i].getIcon() == queend)
					s += "q";
				else if (chessBoardSquares[j][i].getIcon() == bishopl)
					s += "B";
				else if (chessBoardSquares[j][i].getIcon() == bishopd)
					s += "b";
				else if (chessBoardSquares[j][i].getIcon() == empty) {
					s += "1";
				}
				if (j == 7)
					s += "/";
			}
		}

		String s2 = "";
		List<String> l = new ArrayList<>();
		char[] s1 = s.toCharArray();

		for (int i = 0; i < s.length(); i++) {
			do {
				s2 += s1[i];
				i++;
			} while (s1[i] != '/');
			l.add(s2);
			s2 = "";
		}
		s = "";
		for (int k = l.size() - 1; k >= 0; k--) {
			s += l.get(k);
			s += "/";
		}
		s = s.replaceAll("11111111", "8");
		s = s.replaceAll("1111111", "7");
		s = s.replaceAll("111111", "6");
		s = s.replaceAll("11111", "5");
		s = s.replaceAll("1111", "4");
		s = s.replaceAll("111", "3");
		s = s.replaceAll("11", "2");
		s = s.substring(0, s.length() - 1);

		return s;
	}

	/**
	 * fills the pieces( icons of the buttons) in the board in a list from the FEN
	 * format
	 */
	public void fillpiecestolist(List<ImageIcon> l, String s) {
		String a = "d";
		s = a + s;// because charat only accepts legth+1
		int k = 0;
		int i, y = 0, x = 0;
		for (i = s.length() - 1; i > 0; i--) {

			char t = s.charAt(i);// rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
			if (t == 'R') {
				l.add(rookl);
			} else if (t == 'N')
				l.add(knightl);
			else if (t == 'B')
				l.add(bishopl);
			else if (t == 'Q')
				l.add(queenl);
			else if (t == 'K')
				l.add(kingl);
			else if (t == 'P')
				l.add(pawnl);
			else if (t == 'r')
				l.add(rookd);
			else if (t == 'n')
				l.add(knightd);
			else if (t == 'b')
				l.add(bishopd);
			else if (t == 'q')
				l.add(queend);
			else if (t == 'k')
				l.add(kingd);
			else if (t == 'p')
				l.add(pawnd);
			else if (t == '1')
				l.add(empty);
			else if (t == '2') {
				l.add(empty);
				l.add(empty);
			} else if (t == '3')
				for (int j = 0; j < 3; ++j) {
					l.add(empty);
				}
			else if (t == '4')
				for (int j = 0; j < 4; ++j) {
					l.add(empty);
				}
			else if (t == '5')
				for (int j = 0; j < 5; ++j) {
					l.add(empty);
				}
			else if (t == '6')
				for (int j = 0; j < 6; j++) {
					l.add(empty);
				}
			else if (t == '7')
				for (int j = 0; j < 7; j++) {
					l.add(empty);
				}
			else if (t == '8')
				for (int j = 0; j < 8; ++j) {
					l.add(empty);
				}
			else if (t == '/')
				k++;
			else {
			}

		}
	}

	/**
	 * fills the buttons array with pieces ( icons of the buttons) from the list
	 */
	public void filllisttoarray(List<ImageIcon> l) {
		int x = 0, y = 0;
		for (int i = l.size() - 1; i > -1; i--) {
			chessBoardSquares[x][y].setIcon(l.get(i));
			if (x == 7) {
				y++;
				x = 0;
			} else
				x++;

			if (y > 7)
				break;
		}

	}

	/**
	 * @return the option number from the four pieces of promotion
	 */
	public int promotion() {
		int h;
		Object[] options = { queenl, rookl, bishopl, knightl };
		h = JOptionPane.showOptionDialog(bord, "choose piece for promotion ", "promotion",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

		return h;
	}

	public welcome() {
		tx.setFont(new Font(Font.SERIF, Font.PLAIN, 20));

		Stockfish o = new Stockfish();

		setupcontrols();
		controles.setSize(300, 60);
		alp.setFont(new Font("Arial", Font.PLAIN, 25));
		numm.setFont(new Font("Arial", Font.PLAIN, 25));
		bord.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new LineBorder(Color.BLACK)));
		fillboard(bord);
		fillpiecestolist(list, board.getFen());
		filllisttoarray(list);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		bordnumalph.add(bord, BorderLayout.CENTER);
		bordnumalph.add(alp, BorderLayout.SOUTH);
		bordnumalph.add(numm, BorderLayout.EAST);
		info.setSize(240, 480);
		bordnumalph.setSize(500, 500);
		text.add(scroll, BorderLayout.CENTER);//
		text.add(controles, BorderLayout.SOUTH);
		info.add(text, BorderLayout.NORTH);
		info.add(bests, BorderLayout.CENTER);
		tx.setLineWrap(true);
		this.add(bordnumalph, BorderLayout.CENTER);
		this.add(info, BorderLayout.EAST);
		this.setSize(1000, 600);
		this.setResizable(true);

		for (int i = 0; i < chessBoardSquares.length; i++) {
			for (int j = 0; j < chessBoardSquares[i].length; j++) {
				chessBoardSquares[i][j].addActionListener(this);
			}
		}
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		createMenuBar();

		bestmove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (!bestmoveclicked) {
					o.startEngine();
					o.sendCommand("uci");
					o.sendCommand("ucinewgame");
					bestmoveclicked = true;
				}
				best = o.getBestMove(board.getFen(), 10);
				bestmoves.add(best);
				tx.setText(tx.getText() + "\n best move : " + best);
			}
		});
		Dobestmove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Move m = stringtomove(bestmoves.get(bestmoves.size() - 1));
					board.doMove(m);
					moveslist.add(m);
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
				} catch (Exception e) {
				}
			}
		});
		score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (!bestmoveclicked) {
					o.startEngine();
					o.sendCommand("uci");
					o.sendCommand("ucinewgame");
					bestmoveclicked = true;
				}
				float s = 0;
				try {
					s = o.getEvalScore(board.getFen(), 10);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				tx.setText(tx.getText() + "\n score : " + s);
			}
		});

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (backcount != 0) {
					board.doMove(moveslist.get(moveslist.size() - backcount));

					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
					backcount--;
				} else if (startcount > 0 && startclicked) {
					startcount--;
					if (startcount == 0)
						startclicked = false;

					try {
						board.doMove(moveslist.get(moveslist.size() - 1 - startcount));
					} catch (IndexOutOfBoundsException e) {
						System.out.println("eer");
					}
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
				}
				if (pgnclicked && pgncounter < pgnmoves.size()) {

					try {
						board.doMove(pgnmoves.get(pgncounter));
					} catch (IndexOutOfBoundsException e) {
						System.out.println("index");
					}
					pgncounter++;
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
					tx.setText(tx.getText() + "\n" + "eval: " + eval.get(pgncounter) + " " + notes.get(pgncounter)
							+ "/ bestmove : " + bestmoves.get(pgncounter));

				}
				if (board.isInsufficientMaterial()) {
					JOptionPane.showConfirmDialog(null, InsufficientMaterial, "ScreenPreview",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				if (board.isStaleMate()) {
					JOptionPane.showConfirmDialog(null, stalemate, "ScreenPreview", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				}
				if (board.isMated()) {
					if (board.getSideToMove() == Side.BLACK)
						JOptionPane.showConfirmDialog(null, whitemate, "ScreenPreview", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE);
				}
				if (board.isMated()) {
					if (board.getSideToMove() == Side.WHITE)
						JOptionPane.showConfirmDialog(null, blackmate, "ScreenPreview", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (!startclicked && !moveslist.isEmpty()
						&& !board.getFen().contains("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")) {

					board.undoMove();
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
					backcount++;
				}
				if (pgnclicked) {
					if (pgncounter != 0) {
						board.undoMove();
						pgncounter--;
						fillpiecestolist(list1, board.getFen());
						filllisttoarray(list1);
					}
				}

			}
		});
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int i = moveslist.size();
				if (!board.getFen().contains("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR") && backcount == 0) {
					while (i != 0) {
						board.undoMove();
						i--;

					}
					startclicked = true;
					startcount = moveslist.size();
					backcount = 0;
					System.out.println(backcount + "       " + startcount + startcount);
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
				}
			}
		});
		last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				while (!board.getFen().contains("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")) {
					board.undoMove();
				}

				for (Move i : moveslist) {
					board.doMove(i);
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
				}
				startcount = 0;
				backcount = 0;
				startclicked = false;

			}
		});

	}

	public static void main(String[] args) {
		new welcome();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<String> s = new ArrayList<String>();
		JButton o = (JButton) e.getSource();
		boolean setp = false;
		if (!pgnclicked) {
			if (permut != null && permut != o) {
				s.add(squarename(permut));
				o.setIcon(permut.getIcon());
				permut.setIcon(empty);
				setp = true;

			}
			s.add(squarename(o));
			if (permut == null && o.getIcon() != empty) {
				colorlegalmoves(true, o);
				permut = o;
				o = null;
			}
			if (setp) {
				permut = null;
			}

			// System.out.println(bordtofen());
			// System.out.println(fens);

			if (s.size() > 1) {

				Move movetest = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)));
				int moves = 0;
				// JOptionPane.showConfirmDialog(null, blackmate, "ScreenPreview",
				// JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				if (ispromotion(s.get(0), s.get(1), o) == "l")
					moves = 3;
				else if (ispromotion(s.get(0), s.get(1), o) == "d")
					moves = 4;
				else if (!board.isMoveLegal(movetest, true))
					moves = 1;
				else if (board.getContext().isCastleMove(movetest))
					moves = 2;
				else if (ispromotion(s.get(0), s.get(1), o) == "l")
					moves = 3;
				else if (ispromotion(s.get(0), s.get(1), o) == "d")
					moves = 4;
				switch (moves) {
				case 0: {
					if (!startclicked) {
						moveslist.add(movetest);
						board.doMove(movetest);

						System.out.println(board.getFen() + board.getHalfMoveCounter());
					} else
						JOptionPane.showMessageDialog(bord, "you clicked on start click on next to continue");
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
					break;
				}
				case 1: {
					JOptionPane.showMessageDialog(bord, "illegal move");
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);
					break;
				}
				case 2: {
					moveslist.add(movetest);
					board.doMove(movetest);
					fillpiecestolist(list1, board.getFen());
					filllisttoarray(list1);

					System.out.println("\n\\n\\n\\n\\ncastle" + board.getFen());
					break;
				}
				case 3: {
					int n = 0;
					switch (promotion()) {
					case 0:
						o.setIcon(queenl);
						break;
					case 1:
						n = 1;
						o.setIcon(rookl);
						break;
					case 2:
						n = 2;
						o.setIcon(bishopl);
						break;
					case 3:
						n = 3;
						o.setIcon(knightl);
						break;
					}
					switch (n) {
					case 0:
						Move move = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.WHITE_QUEEN);
						moveslist.add(move);
						board.doMove(move);

						break;
					case 1:
						Move move1 = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.WHITE_ROOK);
						moveslist.add(move1);
						board.doMove(move1);

						break;
					case 2:
						Move move2 = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.WHITE_BISHOP);
						moveslist.add(move2);
						board.doMove(move2);

						break;
					case 3:
						Move move3 = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.WHITE_KNIGHT);
						moveslist.add(move3);
						board.doMove(move3);
						break;
					}

					System.out.println("board fen  " + board.getFen());
					break;
				}
				case 4: {
					int n = 0;
					switch (promotion()) {
					case 0:
						o.setIcon(queend);
						break;
					case 1:
						n = 1;
						o.setIcon(rookd);
						break;
					case 2:
						n = 2;
						o.setIcon(bishopd);
						break;
					case 3:
						n = 3;
						o.setIcon(knightd);
						break;
					}
					switch (n) {
					case 0:
						Move move = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.BLACK_QUEEN);
						moveslist.add(move);
						board.doMove(move);

						break;
					case 1:
						Move move1 = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.BLACK_ROOK);
						moveslist.add(move1);
						board.doMove(move1);

						break;
					case 2:
						Move move2 = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.BLACK_BISHOP);
						moveslist.add(move2);
						board.doMove(move2);

						break;
					case 3:
						Move move3 = new Move(Square.valueOf(s.get(0)), Square.valueOf(s.get(1)), Piece.BLACK_KNIGHT);
						moveslist.add(move3);
						board.doMove(move3);
						break;
					}
					System.out.println("board fen  " + board.getFen());
					break;
				}
				}

				/*
				 * MoveList moves = new MoveList() ; MoveGenerator.generatePawnMoves(board,
				 * moves); String s0=moves.toString();
				 */
				if (board.isInsufficientMaterial()) {
					JOptionPane.showConfirmDialog(null, InsufficientMaterial, "ScreenPreview",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				if (board.isStaleMate()) {
					JOptionPane.showConfirmDialog(null, stalemate, "ScreenPreview", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				}
				if (board.isMated()) {
					if (board.getSideToMove() == Side.BLACK)
						JOptionPane.showConfirmDialog(null, whitemate, "ScreenPreview", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE);
				}
				if (board.isMated()) {
					if (board.getSideToMove() == Side.WHITE)
						JOptionPane.showConfirmDialog(null, blackmate, "ScreenPreview", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE);
				}
				while (backcount != 0) {
					System.out.println(board.getFen());
					moveslist.remove(moveslist.size() - 1);
					backcount--;
				}
			}
		}
	}
}
