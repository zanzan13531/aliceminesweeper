import java.util.Random;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Minesweeper {

	HashSet<Integer> mineNums;
	int numMines;
	int rows;
	int cols;
	Random rand = new Random();
	int[][] grid;

	private JFrame mFrame;

	// constructor, takes in number of rows, columns, and mines
	public Minesweeper(int r, int c, int mines) {
		rows = r;
		cols = c;
		numMines = mines;
		grid = minesweeperMaker(); // x, y

		mFrame = new JFrame("AliceSweeper");
      	mFrame.setSize(400,400);
      	mFrame.setLayout(new GridLayout(3, 1));
      
      	mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}

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

		printify(g); // prints just mines

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
