import java.util.Random;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Minesweeper {

	// mineButton nestled class
	class mineButton extends JButton {

		int val;
		int[] loc;
		boolean exposed;

		public mineButton() {
			super();
			val = 0;
			loc = new int[2];
			exposed = false;
			// format();
		}

		public mineButton(int value) {
			super();
			val = value;
			loc = new int[2];
			exposed = false;
			// format();
		}

		private void format() {
			setText("" + val);
			if (val == -1) {
				setBackground(Color.RED);
			}
		}

		public void setVal(int value) {
			val = value;
		}

		public int getVal() {
			return (val);
		}

		public void setLoc(int x, int y) {
			loc[0] = x;
			loc[1] = y;
		}

		public int[] getLoc() {
			return loc;
		}

		public void expose() {
			exposed = true;
			format();
		}

		public boolean isExposed() {
			return exposed;
		}

	}

	class MineListener implements MouseListener {

		Minesweeper m;

		public MineListener(Minesweeper mine) {
			m = mine;
		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {
			mineButton b = (mineButton) e.getSource();
			b.expose();
			if (b.getVal() == -1) {
				m.endGame();
			}
			if (b.getVal() == 0) {
				// expose adjacents
			}
		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

	}

	// fields
	HashSet<Integer> mineNums;
	int numMines;
	int rows;
	int cols;
	Random rand = new Random();
	int[][] grid;
	mineButton[][] buttons;

	private JFrame mFrame;

	// constructor, takes in number of rows, columns, and mines
	public Minesweeper(int r, int c, int mines) {
		rows = r;
		cols = c;
		numMines = mines;
		grid = minesweeperMaker(); // x, y

		// JFrame setup
		mFrame = new JFrame("AliceSweeper");
		// mFrame.setSize(400, 400);

		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel p = panelMaker();

		// add p to frame
		mFrame.add(p);

		// pack frame
		mFrame.pack();

		// make visible
		mFrame.setVisible(true);
	}

	// builds the panel with all buttons
	private JPanel panelMaker() {

		buttons = new mineButton[rows][cols];

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(rows, cols));

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				mineButton b = new mineButton(grid[x][y]);
				b.setLoc(x, y);
				b.addMouseListener(new MineListener(this));
				b.setPreferredSize(new Dimension(50, 50));
				buttons[x][y] = b;
				p.add(b);
			}
		}

		return (p);
	}

	// builds the array of mines
	private int[][] minesweeperMaker() {

		mineNums = new HashSet<Integer>();
		int[][] g = new int[cols][rows];

		// generates mine locations
		for (int i = 0; i < numMines; i++) {
			int m = rand.nextInt(rows * cols);
			while (mineNums.contains(m)) {
				m = rand.nextInt(rows * cols);
			}
			mineNums.add(m);
		}

		// places mines on board
		for (int i : mineNums) {
			g[i / rows][i % rows] = -1;
		}

		// printify(g); // prints just mines

		// counts number of adjacent mines per tile
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				g[x][y] = countMines(g, x, y);
			}
		}

		return (g);
	}

	// counts the number of surrounding mines
	private int countMines(int[][] g, int x, int y) {
		int m = 0;

		// return if current tile is already a mine
		if (g[x][y] == -1) {
			return (-1);
		}

		if (x >= 1) {
			if (y >= 1) {
				// check top left
				if (g[x - 1][y - 1] == -1) {
					m++;
				}
			}
			// check mid left
			if (g[x - 1][y] == -1) {
				m++;
			}
			// check bottom left
			if (y < rows - 1) {
				if (g[x - 1][y + 1] == -1) {
					m++;
				}
			}
		}
		if (y >= 1) {
			// check top mid
			if (g[x][y - 1] == -1) {
				m++;
			}
		}
		if (y < rows - 1) {
			// check bottom mid
			if (g[x][y + 1] == -1) {
				m++;
			}
		}
		if (x < cols - 1) {
			if (y >= 1) {
				// check top right
				if (g[x + 1][y - 1] == -1) {
					m++;
				}
			}
			// check mid right
			if (g[x + 1][y] == -1) {
				m++;
			}
			if (y < rows - 1) {
				// check bottom right
				if (g[x + 1][y + 1] == -1) {
					m++;
				}
			}
		}

		return (m);
	}

	// ends the game if a mine is clicked
	private void endGame() {
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				buttons[x][y].format();
			}
		}
	}

	// print function
	public void printify(int[][] g) {
		for (int x = 0; x < cols; x++) {
			System.out.print("|");
			for (int y = 0; y < rows; y++) {
				if (g[x][y] >= 0) {
					System.out.print(" ");
				}
				System.out.print(g[x][y] + "|");
			}
			System.out.println();
		}
		System.out.println();
	}

	// alternate print function
	public void printify() {
		for (int x = 0; x < cols; x++) {
			System.out.print("|");
			for (int y = 0; y < rows; y++) {
				if (grid[x][y] >= 0) {
					System.out.print(" ");
				}
				System.out.print(grid[x][y] + "|");
			}
			System.out.println();
		}
		System.out.println();
	}
}
