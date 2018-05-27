package foundation;


public class GOLBoard {

    public static int CELLSHORIZONTAL = 100; //columns
    public static int CELLSVERTICAL = 100; //rows
    public static char REPRESENTATIONLIVECELL = '+';
    public static char REPRESENTATIONDEADCELL = '.';

    private CellState[][] board = new CellState[CELLSHORIZONTAL][CELLSVERTICAL];

    public GOLBoard() {
        for (int i = 0; i < board.length; ++i)
            for (int j = 0; j < board[i].length; ++j)
                board[i][j] = CellState.DEAD;

    }

    public GOLBoard(String filename) {

        for (int i = 0; i < board.length; ++i)
            for (int j = 0; j < board[i].length; ++j)
                board[i][j] = CellState.DEAD;

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
                    setCellState(startH + y, startV + x, CellState.DEAD);
                } else if (gol[y][x] == REPRESENTATIONLIVECELL) {
                    setCellState(startH + y, startV + x, CellState.LIVE);

                } else {
                    throw new GOLFileException("Illegal characters.");
                }
            }
        }

    }

    public GOLBoard(GOLBoard previous) {

        // Read all cells in board
        for (int y = 0; y < CELLSHORIZONTAL; y++) {
            for (int x = 0; x < CELLSVERTICAL; x++) {

                // Check the previous cell states with its neighbours' state in order to judge the state of current cell states
                if (isLive(previous, previous.getCellState(y, x), y, x)) {
                    board[y][x] = CellState.LIVE;
                } else {
                    board[y][x] = CellState.DEAD;
                }
            }
        }


    }

    public boolean isLive(GOLBoard previous, CellState previousCellState, int y, int x) {
        int liveneighbors = 0;

        // Count the number of live neighbors
        // The row of neighbors are from x-1 to x+1
        for (int i = x - 1; i <= x + 1; i++) {
            // The column of neighbor are from y-1 to y+1
            for (int j = y - 1; j <= y + 1; j++) {
                // Do not count the cell itself
                if (i == x && j == y) {
                }
                // Do not count if the neghbors are out of the board
                else if (i < 0 || i > CELLSVERTICAL - 1 || j < 0 || j > CELLSHORIZONTAL - 1) {
                }
                // Add 1 to liveneighbors if a neighbor is alive
                else {
                    if (previous.getCellState(j, i) == CellState.LIVE) {
                        liveneighbors++;
                    }
                }

            }
        }

        //
        if (previousCellState == CellState.LIVE) {
            // Any live cell with fewer than two or more than three live neighbors dies
            if (liveneighbors < 2 || liveneighbors > 3) {
                return false;
            }
            // Any live cell with two or three live neighbors lives on to the next generation.
            else {
                return true;
            }
        }
        // Any dead cell with exactly three live neighbors becomes a live cell
        else {
            if (liveneighbors == 3) {
                return true;
            } else {
                return false;
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
