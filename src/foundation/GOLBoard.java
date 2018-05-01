package foundation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.apple.laf.AquaButtonExtendedTypes.SegmentedNamedBorder;

public class GOLBoard {

	public static int CELLSHORIZONTAL = 100;
	public static int CELLSVERTICAL = 100;
	public static char REPRESENTATIONLIVECELL = '+';
	public static char REPRESENTATIONDEADCELL = '.';

	private CellState[][] board = new CellState[CELLSHORIZONTAL][CELLSVERTICAL];

	public GOLBoard() {
		for (int i = 0; i < board.length; ++i)
			for (int j = 0; j < board[i].length; ++j)
				board[i][j] = CellState.DEAD;

	}

	// loads the initial configuration from a GOL file and centers it on
	// the board
	// GOLFileException is thrown if
	// - the file cannot be opened or read
	// - the file contains illegal characters (different from
	// REPRESENTATIONLIVECELL, REPRESENTATIONDEADCELL)
	// - the width of text lines varies
	// - the text lines have a length of 0 or > CELLSHORIZONTAL
	// - there are more lines than CELLSVERTICAL

	public GOLBoard(String filename) {

		// Get the size of the configuration file

		int length = filename.length();
		System.out.println(length);

		// Checking for exception

		int rows = 1;
		int cols = filename.indexOf("\n");
		int temp = 0;

		System.out.println(cols);
		
		for (int i = 0; i < length; i++) {
//			if (filename.charAt(i) != REPRESENTATIONDEADCELL || filename.charAt(i) != REPRESENTATIONLIVECELL) {
//				throw new GOLFileException("Illegal characters");
//			}			
			if (filename.charAt(i) == '\n') {
				if (temp != cols) {
					
					throw new GOLFileException("the width of text line is not consistant",rows);
				
				} else {
					temp = 0;
					rows++;
				}
			}
			temp++;
		}

		// int cols = filename.indexOf("\n");
		// int rows = length / (cols + 1);

		// System.out.println(cols);
		// System.out.println(rows);

		if (rows > CELLSVERTICAL) {
			throw new GOLFileException("there are more lines than CELLSVERTICAL");
		} else if (cols > CELLSHORIZONTAL) {
			throw new GOLFileException("there are more lines than CELLSHORIZONTAL");
		}

		char[][] gol = new char[cols][rows];

		int row = 0;
		int col = 0;

		for (int i = 0; i < length; i++) {
			if (filename.charAt(i) == '\n') {
				row++;
				col = 0;
			} else {

				gol[col][row] = filename.charAt(i);
				col++;

			}

		}

		// set the cellstate for cells in gol file
		int startH = CELLSHORIZONTAL / 2 - cols / 2;
		int startV = CELLSVERTICAL / 2 - rows / 2;

		// int live = 0;

		for (int y = 0; y < cols; y++) {
			for (int x = 0; x < rows; x++) {
				if (gol[y][x] == REPRESENTATIONDEADCELL) {
					setCellState(startH + y, startV + x, CellState.DEAD);// dead++;
				} else {
					setCellState(startH + y, startV + x, CellState.LIVE);

				}
			}
		}

	}

	public CellState getCellState(int col, int row) {

		return board[col][row];
	}

	public void setCellState(int col, int row, CellState value) {

		board[col][row] = value;
	}

}
