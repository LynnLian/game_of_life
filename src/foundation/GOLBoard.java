package foundation;


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

        // Checking for exception

        int rows = 0;
        int cols = filename.indexOf("\n");
        int temp = 0;


        for (int i = 0; i < length; i++) {

            if (filename.charAt(i) == '\n') {
                if (temp != cols) {
                    throw new GOLFileException("the width of text line is not consistent", rows + 1);

                } else {
                    temp = 0;
                    rows++;
                }
            } else {
                temp++;
            }

        }


        if (rows > CELLSVERTICAL) {
            throw new GOLFileException("There are more lines than CELLSVERTICAL");
        } else if (cols > CELLSHORIZONTAL) {
            throw new GOLFileException("There are more lines than CELLSHORIZONTAL");
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


        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                if (gol[y][x] == REPRESENTATIONDEADCELL) {
                    setCellState(startH + y, startV + x, CellState.DEAD);// dead++;
                } else if (gol[y][x] == REPRESENTATIONLIVECELL) {
                    setCellState(startH + y, startV + x, CellState.LIVE);

                } else {
                    throw new GOLFileException("Illegal characters.");
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
