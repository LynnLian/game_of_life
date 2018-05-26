package foundation;


public class GOLBoard {

    public static int CELLSHORIZONTAL = 5; //columns
    public static int CELLSVERTICAL = 5; //rows
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

        for (int y = 0; y < CELLSHORIZONTAL; y++) {
            for (int x = 0; x < CELLSVERTICAL; x++) {

//                System.out.println("cellstate: " + previous.getCellState(y, x));

                if (isLive(previous,previous.getCellState(y, x), y, x)) {
//                    System.out.println("True");
                    board[y][x] = CellState.LIVE;
                } else {
                    board[y][x] = CellState.DEAD;
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

    public boolean isLive(GOLBoard previous, CellState state, int y, int x) {
        int liveneighbors = 0;


        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i == x && j == y) {
//                    System.out.println("Self");
                } else if (i < 0 || i > CELLSVERTICAL - 1 || j < 0 || j > CELLSHORIZONTAL - 1) {
//                    System.out.println("i: " + i + " j: " + j);
                } else {
                    if (previous.getCellState(j, i) == CellState.LIVE) {
                        liveneighbors++;
                    }
                }

            }
        }

//        if (liveneighbors > 0) {
//            System.out.println("Live neighbours is " + liveneighbors);
//            System.out.println("Location: x: " + x + " y: " + y);
//        }


        if (state == CellState.LIVE) {
            if (liveneighbors < 2 || liveneighbors > 3) {
                return false;
            } else {
                return true;
            }
        } else {
            if (liveneighbors == 3) {
                return true;
            } else {
                return false;
            }
        }

    }


}
